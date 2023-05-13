package com.nexus.service;

import com.nexus.entity.Card;

public interface CardService {

    Card createCard (Card card);
    String generateCardNumber(Long id);

    void activateCard(String cardNumber);

}
