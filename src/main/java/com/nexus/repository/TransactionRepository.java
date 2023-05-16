package com.nexus.repository;

import com.nexus.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionNumberAndCardNumber(String transactionNumber,String cardNumber);
    Transaction findByTransactionNumber(String transactionNumber);
}
