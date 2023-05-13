package com.nexus.service.impl;

import com.nexus.entity.Card;
import com.nexus.repository.CardRepository;
import com.nexus.service.CardService;
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
    }
}
