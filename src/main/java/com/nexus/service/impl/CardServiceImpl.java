package com.nexus.service.impl;

import com.nexus.entity.Card;
import com.nexus.repository.CardRepository;
import com.nexus.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

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
        int mont = currentDate.get(Calendar.MONTH);
        StringBuilder str = new StringBuilder();
        str.append(mont);
        str.append("/");
        str.append(year);
        card.setExpirationDate(str.toString());

        card.setActive(false);
        card.setBlocked(true);
        return cardRepository.save(card);
    }
}
