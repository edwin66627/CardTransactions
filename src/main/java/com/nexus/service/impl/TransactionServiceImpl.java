package com.nexus.service.impl;

import com.nexus.constant.CardMessage;
import com.nexus.constant.TransactionMessage;
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
            throw new NoSuchElementException(String.format(CardMessage.NO_SUCH_ELEMENT, "number", cardNumber));
        }
        if(cardInDB.getBalance() < amount){
            throw new IllegalArgumentException(TransactionMessage.NOT_ENOUGH_BALANCE);
        }
        if(!cardInDB.isActive()){
            throw new IllegalArgumentException(CardMessage.NOT_ACTIVATED);
        }
        if(cardInDB.isBlocked()){
            throw new IllegalArgumentException(CardMessage.BLOCKED);
        }
        Date currentDate = new Date();
        int comparisonResult = currentDate.compareTo(cardInDB.getFullExpirationDate());
        if (comparisonResult > 0) {
            throw new IllegalArgumentException(CardMessage.EXPIRED);
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
                .orElseThrow(() ->new NoSuchElementException(String.format(TransactionMessage.NO_ELEMENT_BY_ID,
                        id)));

        return transaction;
    }

    @Override
    public void cancelTransaction(Long transactionId, String cardNumber) throws NoSuchElementException{
        Transaction transactionInDB = transactionRepository.findByIdAndCardNumber(transactionId,cardNumber);
        if(transactionInDB == null){
            throw new NoSuchElementException(String.format(TransactionMessage.NO_ELEMENT_BY_ID_AND_NUMBER,
                    transactionId,cardNumber));
        }
        Date currentDate = new Date();
        Date transactionDate = transactionInDB.getCreated();
        long timeDifference = currentDate.getTime() - transactionDate.getTime();
        long daysDifference = (timeDifference / (1000 * 60 * 60 * 24)) % 365;

        if(daysDifference> 1){
            throw new IllegalArgumentException(TransactionMessage.INVALID_TIME_TO_CANCEL);
        }

        Card cardInDB = cardRepository.findByCardNumber(cardNumber);

        double oldBalance = cardInDB.getBalance();
        cardInDB.setBalance(transactionInDB.getAmount() + oldBalance);
        transactionInDB.setStatus("Canceled");
        cardRepository.save(cardInDB);
        transactionRepository.save(transactionInDB);

    }


}
