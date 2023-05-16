package com.nexus.controller;

import com.nexus.constant.TransactionMessage;
import com.nexus.dto.CancelTransactionRequestDTO;
import com.nexus.dto.SaveTransactionRequestDTO;
import com.nexus.dto.TransactionDTO;
import com.nexus.entity.HttpResponse;
import com.nexus.entity.Transaction;
import com.nexus.service.TransactionService;
import com.nexus.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Save a Transaction", description = "Save a Transaction and subtract funds from Card balance")
    private ResponseEntity<HttpResponse> saveTransaction(
            @Parameter(description = "Transaction information to be saved. Card number of the Card to be charged. Amount" +
                    "to be charged, which minimum value is 1 USD")
            @Valid @RequestBody SaveTransactionRequestDTO saveTransactionDTO){
        transactionService.saveTransaction(saveTransactionDTO.getCardNumber(), saveTransactionDTO.getAmount());
        return ResponseUtility.buildResponse(TransactionMessage.SAVE_TRANSACTION_DONE, OK);
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Get a Transaction", description = "Get details of an existing Transaction")
    private ResponseEntity<TransactionDTO> getTransaction(
            @Parameter(description = "Transaction id field to fetch Transaction information")
            @PathVariable Long transactionId){
        Transaction transaction = transactionService.getTransaction(transactionId);
        return new ResponseEntity<>(mapper.map(transaction, TransactionDTO.class), OK);
    }

    @PostMapping("/annulation")
    @Operation(summary = "Cancel a Transaction",
            description = "Cancel a Transaction, as long as, it's not more than one day old")
    private ResponseEntity<HttpResponse> cancelTransaction(
            @Parameter(description = "Transaction id field to fetch Transaction. " +
                    "Card number of the Card to which the funds have to be returned")
            @Valid @RequestBody CancelTransactionRequestDTO cancelTransactionDTO){
        transactionService.cancelTransaction(cancelTransactionDTO.getTransactionId(), cancelTransactionDTO.getCardNumber());
        return ResponseUtility.buildResponse(TransactionMessage.CANCEL_TRANSACTION_DONE, OK);
    }

}
