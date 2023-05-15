package com.nexus.controller;

import com.nexus.constant.TransactionMessage;
import com.nexus.dto.CancelTransactionRequestDTO;
import com.nexus.dto.SaveTransactionRequestDTO;
import com.nexus.dto.TransactionDTO;
import com.nexus.entity.HttpResponse;
import com.nexus.entity.Transaction;
import com.nexus.service.TransactionService;
import com.nexus.utils.ResponseUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private ModelMapper mapper;
    @Autowired
    public TransactionController(TransactionService transactionService, ModelMapper mapper) {
        this.transactionService = transactionService;
        this.mapper = mapper;
    }

    @PostMapping("/purchase")
    private ResponseEntity<HttpResponse> saveTransaction(@Valid @RequestBody SaveTransactionRequestDTO saveTransactionDTO){
        transactionService.saveTransaction(saveTransactionDTO.getCardNumber(), saveTransactionDTO.getAmount());
        return ResponseUtility.buildResponse(TransactionMessage.SAVE_TRANSACTION_DONE, OK);
    }

    @GetMapping("/{transactionId}")
    private ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long transactionId){
        Transaction transaction = transactionService.getTransaction(transactionId);
        return new ResponseEntity<>(mapper.map(transaction, TransactionDTO.class), OK);
    }

    @PostMapping("/annulation")
    private ResponseEntity<HttpResponse> cancelTransaction(@Valid @RequestBody CancelTransactionRequestDTO cancelTransactionDTO){
        transactionService.cancelTransaction(cancelTransactionDTO.getTransactionId(), cancelTransactionDTO.getCardNumber());
        return ResponseUtility.buildResponse(TransactionMessage.CANCEL_TRANSACTION_DONE, OK);
    }

}
