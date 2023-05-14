package com.nexus.controller;

import com.nexus.entity.Transaction;
import com.nexus.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/purchase")
    private ResponseEntity<String> saveTransaction(@RequestBody Transaction transaction){
        transactionService.saveTransaction(transaction.getCardNumber(), transaction.getAmount());
        return new ResponseEntity("Transaction was successful", OK);
    }

    @GetMapping("/{transactionId}")
    private ResponseEntity<Transaction> getTransaction(@PathVariable Long transactionId){
        Transaction transaction = transactionService.getTransaction(transactionId);
        return new ResponseEntity<>(transaction, OK);
    }

}
