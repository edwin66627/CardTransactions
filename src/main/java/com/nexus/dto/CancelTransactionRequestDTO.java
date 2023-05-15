package com.nexus.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CancelTransactionRequestDTO {
    @NotNull(message = "Transaction id is mandatory to cancel the transaction")
    private Long transactionId;
    @NotEmpty(message = "Card number is mandatory to cancel the transaction")
    private String cardNumber;

    public CancelTransactionRequestDTO(){}

    public CancelTransactionRequestDTO(Long transactionId, String cardNumber) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
