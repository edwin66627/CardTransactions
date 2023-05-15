package com.nexus.sample.data;

import com.nexus.dto.CancelTransactionRequestDTO;
import com.nexus.dto.SaveTransactionRequestDTO;
import com.nexus.dto.TransactionDTO;
import com.nexus.entity.Transaction;

import java.util.Date;

public class TransactionData {
    public static final String CARD_NUMBER = "1001529311360714";
    public static final double AMOUNT = 2;
    public static final Long TRANSACTION_ID = 1L;
    public static final String METHOD = "credit";
    public static final SaveTransactionRequestDTO getTransactionToSave(){
        SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO();
        requestDTO.setCardNumber(CARD_NUMBER);
        requestDTO.setAmount(AMOUNT);
        return requestDTO;
    }

    public static final Transaction getTransactionData(){
        Transaction transaction = new Transaction();
        transaction.setId(TRANSACTION_ID);
        transaction.setMethod(METHOD);
        transaction.setAmount(AMOUNT);
        transaction.setCreated(new Date());

        return transaction;
    }

    public static final TransactionDTO getTransactionDataDTO(){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(TRANSACTION_ID);
        transactionDTO.setMethod(METHOD);
        transactionDTO.setAmount(AMOUNT);
        return transactionDTO;
    }

    public static final CancelTransactionRequestDTO getCancelTrasactionData(){
        CancelTransactionRequestDTO requestDTO = new CancelTransactionRequestDTO();
        requestDTO.setTransactionId(TRANSACTION_ID);
        requestDTO.setCardNumber(CARD_NUMBER);
        return requestDTO;
    }
}
