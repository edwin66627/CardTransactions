package com.nexus.dto;

import java.util.Date;

public class TransactionDTO {
    private Long id;
    private String method;
    private String currency;

    private double amount;

    private String status;
    private Date created;

    private String cardNumber;

    public TransactionDTO(){}
    public TransactionDTO(Long id, String method, String currency, double amount, String status, Date created, String cardNumber) {
        this.id = id;
        this.method = method;
        this.currency = currency;
        this.amount = amount;
        this.status = status;
        this.created = created;
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
