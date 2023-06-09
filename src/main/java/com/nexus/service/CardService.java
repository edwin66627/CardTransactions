package com.nexus.service;

import com.nexus.entity.Card;

public interface CardService {

    Card createCard (Card card);
    String generateCardNumber(Long id);

    void activateCard(String cardNumber);
    void blockCard(Long id);

    String rechargeBalance(String cardNumber, double balance);
    String getBalance(Long id);

}
