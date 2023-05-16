package com.nexus.service.impl;

import com.nexus.constant.BusinessConstant;
import com.nexus.constant.CardMessage;
import com.nexus.entity.Card;
import com.nexus.repository.CardRepository;
import com.nexus.service.CardService;
import com.nexus.utils.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        Calendar currentDate = Calendar.getInstance();
        card.setIssueDate(currentDate.getTime());

        card.setFullExpirationDate(AppUtility.addYearsToDate(currentDate, BusinessConstant.CARD_VALIDITY_IN_YEARS));
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        card.setExpirationDate(month+"/"+year);

        card.setActive(false);
        card.setBlocked(true);
        return cardRepository.save(card);
    }

    @Override
    public String generateCardNumber(Long id) {
        Card cardInDB = cardRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException(String.format(CardMessage.NO_SUCH_ELEMENT, "id", id)));

        if(cardInDB.getCardNumber() != null){
            throw new IllegalArgumentException(String.format(CardMessage.ALREADY_HAS_NUMBER_ASSIGNED, cardInDB.getCardNumber()));
        }

        Long bottomLimit = 1000000000L;
        Long topLimit = 9000000000L;
        Long randomNumber = AppUtility.generateRandomLongNumber(bottomLimit,topLimit);

        String cardNumber = id + String.valueOf(randomNumber);
        cardInDB.setCardNumber(cardNumber);
        cardRepository.save(cardInDB);
        return cardNumber;
    }

    @Override
    public void activateCard(String cardNumber) {
        Card cardInDB = cardRepository.findByCardNumber(cardNumber);
        if(cardInDB == null){
            throw new NoSuchElementException(String.format(CardMessage.NO_SUCH_ELEMENT, "number", cardNumber));
        }

        cardInDB.setActive(true);
        cardInDB.setBlocked(false);
        cardRepository.save(cardInDB);
    }

    @Override
    public void blockCard(Long id) {
        Card cardInDB = cardRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException(String .format(CardMessage.NO_SUCH_ELEMENT, "id", id)));

        cardInDB.setBlocked(true);
        cardRepository.save(cardInDB);
    }

    @Override
    public String rechargeBalance(String cardNumber, double balance) {
        Card cardInDB = cardRepository.findByCardNumber(cardNumber);
        if(cardInDB == null){
            throw new NoSuchElementException(String.format(CardMessage.NO_SUCH_ELEMENT, "number", cardNumber));
        }
        if(balance < BusinessConstant.MINIMUM_AMOUNT_TO_RECHARGE){
            throw new IllegalArgumentException(CardMessage.MIN_RECHARGE_AMOUNT);
        }

        double newBalance = cardInDB.getBalance() + balance;
        cardInDB.setBalance(newBalance);
        cardRepository.save(cardInDB);

        return String.format("%.2f",newBalance);
    }

    @Override
    public String getBalance(Long id) {
        Card cardInDB = cardRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException(String.format(CardMessage.NO_SUCH_ELEMENT, "id", id)));
        double balance = cardInDB.getBalance();
        return String.format("%.2f",balance);
    }
}
