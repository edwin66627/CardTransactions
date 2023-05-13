package com.nexus.service.impl;

import com.nexus.entity.Card;
import com.nexus.repository.CardRepository;
import com.nexus.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        //Date currentDate = new Date();
        card.setIssueDate(currentDate.getTime());

        currentDate.add(Calendar.YEAR, 3);
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        StringBuilder str = new StringBuilder();
        str.append(month);
        str.append("/");
        str.append(year);
        card.setExpirationDate(str.toString());

        card.setActive(false);
        card.setBlocked(true);
        return cardRepository.save(card);
    }

    @Override
    public String generateCardNumber(Long id) {
        Card cardInDB = cardRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException("Card with ID: "+id+" does not exist"));

        Random random = new Random();
        long number = random.nextLong() % 9000000000L + 1000000000L;
        Math.abs(number);
        String cardNumber = id + String.valueOf(number);
        cardInDB.setcardNumber(cardNumber);
        cardRepository.save(cardInDB);
        return cardNumber;
    }

    @Override
    public void activateCard(String cardNumber) {
        Card cardInDB = cardRepository.findByCardNumber(cardNumber);
        if(cardInDB == null){
            throw new NoSuchElementException("Card with number: "+cardNumber+" does not exist");
        }

        cardInDB.setActive(true);
        cardInDB.setBlocked(false);
        cardRepository.save(cardInDB);
    }

    @Override
    public void blockCard(Long id) {
        Card cardInDB = cardRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException("Card with ID: "+id+" does not exist"));

        cardInDB.setBlocked(true);
        cardRepository.save(cardInDB);
    }

    @Override
    public String rechargeBalance(String cardNumber, double balance) {
        Card cardInDB = cardRepository.findByCardNumber(cardNumber);
        if(cardInDB == null){
            throw new NoSuchElementException("Card with number: "+cardNumber+" does not exist");
        }
        if(balance < 1){
            throw new IllegalArgumentException("Minimum balance amount to recharge is 1 USD");
        }

        double newBalance = cardInDB.getBalance() + balance;
        cardInDB.setBalance(newBalance);
        cardRepository.save(cardInDB);

        return String.format("%.2f",newBalance);
    }

    @Override
    public String getBalance(Long id) {
        Card cardInDB = cardRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException("Card with ID: "+id+" does not exist"));
        double balance = cardInDB.getBalance();
        return String.format("%.2f",balance);
    }
}
