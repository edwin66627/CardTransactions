package com.nexus.service;

import com.nexus.entity.Transaction;

public interface TransactionService {
    String saveTransaction(String cardNumber, double amount);
    Transaction getTransaction(String transactionNumber);
    void cancelTransaction(String transactionNumber, String cardNumber);
}
