package com.nexus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CancelTransactionRequestDTO {
    @NotEmpty(message = "Transaction number is mandatory to cancel the transaction")
    private String transactionNumber;
    @NotEmpty(message = "Card number is mandatory to cancel the transaction")
    private String cardNumber;

    public CancelTransactionRequestDTO(){}

    public CancelTransactionRequestDTO(String transactionNumber, String cardNumber) {
        this.transactionNumber = transactionNumber;
        this.cardNumber = cardNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
