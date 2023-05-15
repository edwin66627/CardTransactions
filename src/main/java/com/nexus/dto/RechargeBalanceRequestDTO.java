package com.nexus.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RechargeBalanceRequestDTO {
    @NotEmpty(message = "Card number is mandatory to make a recharge")
    private String cardNumber;
    @NotNull(message = "An amount is mandatory to make a recharge")
    private double amount;

    public RechargeBalanceRequestDTO(){}
    public RechargeBalanceRequestDTO(String cardNumber, double amount) {
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
