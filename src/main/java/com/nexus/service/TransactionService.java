package com.nexus.service;

import com.nexus.entity.Transaction;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransactionService {
    void saveTransaction(String cardNumber, double amount);
    Transaction getTransaction(Long id);
    void cancelTransaction(Long transactionId, String cardNumber);
}
