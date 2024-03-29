package com.nexus.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cards")
@SequenceGenerator(name = "card_seq", sequenceName = "card_seq", initialValue = 100001)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    private String type;
    private double balance;
    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "full_expiration_date")
    @Temporal(TemporalType.DATE)
    private Date fullExpirationDate;
    private String currency;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "is_blocked")
    private boolean isBlocked;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    public Card(){}
    public Card(Long id, String cardNumber, String type, double balance, Date issueDate, String expirationDate, String currency, boolean isActive, boolean isBlocked) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.type = type;
        this.balance = balance;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.currency = currency;
        this.isActive = isActive;
        this.isBlocked = isBlocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getFullExpirationDate() {
        return fullExpirationDate;
    }

    public void setFullExpirationDate(Date fullExpirationDate) {
        this.fullExpirationDate = fullExpirationDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
