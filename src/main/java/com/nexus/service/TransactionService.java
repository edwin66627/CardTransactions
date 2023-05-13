package com.nexus.service;

public interface TransactionService {
    void saveTransaction(String cardNumber, double amount);
}
