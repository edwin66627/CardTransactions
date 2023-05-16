package com.nexus.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "transaction_number")
    private String transactionNumber;
    private String method;
    private String currency;

    private double amount;

    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "card_number")
    private String cardNumber;

    public Transaction(){}
    public Transaction(Long id, String transactionNumber, String method, String currency, double amount, String status, Date created, String cardNumber) {
        this.id = id;
        this.transactionNumber = transactionNumber;
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

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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
