package com.nexus.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SaveTransactionRequestDTO {
    @NotEmpty(message = "Card number is mandatory to process the transaction")
    private String cardNumber;
    @NotNull(message = "An amount is mandatory to process the transaction")
    @Min(value = 1, message = "Minimum amount is 1 USD")
    private double amount;

    public SaveTransactionRequestDTO(){}

    public SaveTransactionRequestDTO(String cardNumber, double amount) {
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
