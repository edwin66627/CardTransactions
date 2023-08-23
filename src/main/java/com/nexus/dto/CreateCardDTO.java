package com.nexus.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class CreateCardDTO {
    private Long id;

    private String cardNumber;

    @NotEmpty(message = "Type is mandatory")
    @Pattern(regexp = "credit|debit", message = "Valid card types are : credit or debit")
    private String type;
    private double balance;
    @NotEmpty(message = "Client id is mandatory")
    private Long clientId;

    private Date issueDate;
    private String expirationDate;

    private Date fullExpirationDate;
    @NotEmpty(message = "Currency is mandatory")
    @Pattern(regexp = "USD", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Currency must be USD")
    private String currency;
    private boolean isActive;
    private boolean isBlocked;

    public CreateCardDTO(){}

    public CreateCardDTO(Long id, String cardNumber, String type, double balance, Long clientId, Date issueDate, String expirationDate, Date fullExpirationDate, String currency, boolean isActive, boolean isBlocked) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.type = type;
        this.balance = balance;
        this.clientId = clientId;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.fullExpirationDate = fullExpirationDate;
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

    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
