package com.nexus.service.impl;

import com.nexus.entity.Card;
import com.nexus.entity.Transaction;
import com.nexus.repository.CardRepository;
import com.nexus.repository.TransactionRepository;
import com.nexus.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private CardRepository cardRepository;
    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void saveTransaction(String cardNumber, double amount) {
        Card cardInDB = cardRepository.findByCardNumber(cardNumber);
        if(cardInDB == null){
            throw new NoSuchElementException("Card with number: "+cardNumber+" does not exist");
        }
        if(cardInDB.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient balance");
        }
        if(cardInDB.isBlocked()){
            throw new IllegalArgumentException("Card is not activated. The transaction cannot be performed");
        }
        if(cardInDB.isBlocked()){
            throw new IllegalArgumentException("Card is blocked. The transaction cannot be performed");
        }
        Date currentDate = new Date();
        int comparisonResult = currentDate.compareTo(cardInDB.getFullExpirationDate());
        if (comparisonResult > 0) {
            System.out.println("Card has expired. The transaction cannot be performed");
        }

        double currentBalance = cardInDB.getBalance();
        cardInDB.setBalance(currentBalance - amount);

        Transaction transaction = new Transaction();
        transaction.setCreated(currentDate);
        transaction.setMethod(cardInDB.getType());
        transaction.setCurrency(cardInDB.getCurrency());
        transaction.setAmount(amount);
        transaction.setStatus("Accepted");
        transaction.setCardNumber(cardNumber);

        cardRepository.save(cardInDB);
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException("Transaction with ID: "+id+" does not exist"));

        return transaction;
    }

    @Override
    public void cancelTransaction(Long transactionId, String cardNumber) throws NoSuchElementException{
        Transaction transactionInDB = transactionRepository.findByIdAndCardNumber(transactionId,cardNumber);
        if(transactionInDB == null){
            throw new NoSuchElementException("A transaction with Id "+transactionId+ " and card number " +
                cardNumber+ " does not exist");
        }
        Date currentDate = new Date();
        Date transactionDate = transactionInDB.getCreated();
        long timeDifference = currentDate.getTime() - transactionDate.getTime();
        long hoursDifference = (timeDifference / (1000 * 60 * 60)) % 24;

        if(hoursDifference > 24){
            throw new IllegalArgumentException("Transactions can only be canceled within the first 24 hours");
        }

        Card cardInDB = cardRepository.findByCardNumber(cardNumber);

        double oldBalance = cardInDB.getBalance();
        cardInDB.setBalance(transactionInDB.getAmount() + oldBalance);
        transactionInDB.setStatus("Canceled");
        cardRepository.save(cardInDB);
        transactionRepository.save(transactionInDB);
    }


}
