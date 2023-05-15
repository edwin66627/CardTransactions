package com.nexus.dto;

import javax.validation.constraints.NotEmpty;

public class ActivateCardRequestDTO {
    @NotEmpty(message = "Card number is mandatory to activate a card")
    private String cardNumber;

    public ActivateCardRequestDTO(){}
    public ActivateCardRequestDTO(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
