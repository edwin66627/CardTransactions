package com.nexus.service;

import com.nexus.entity.Transaction;

public interface TransactionService {
    void saveTransaction(String cardNumber, double amount);
    Transaction getTransaction(Long id);
}
