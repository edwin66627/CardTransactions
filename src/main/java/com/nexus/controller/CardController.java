package com.nexus.controller;

import com.nexus.entity.Card;
import com.nexus.exception.ExceptionHandling;
import com.nexus.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping(path = { "/", "/card"})
public class CardController extends ExceptionHandling {

    private CardService cardService;
    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
    @PostMapping("/create")
    private ResponseEntity<Card> createCard(@RequestBody Card card){
        Card cardSaved = cardService.createCard(card);
        return new ResponseEntity<>(cardSaved, CREATED);
    }

    @GetMapping("/{productId}/number")
    private ResponseEntity<Map<String,String>> getCardNumber(@PathVariable Long productId){
        String cardNumber = cardService.generateCardNumber(productId);
        return new ResponseEntity<>(Collections.singletonMap("cardNumber", cardNumber), OK);
    }

    @PostMapping("/enroll")
    private ResponseEntity<String> activateCard(@RequestBody Card card){
        cardService.activateCard(card.getCardNumber());
        return new ResponseEntity("Card was successfully activated", OK);
    }

    @DeleteMapping("/{cardId}")
    private ResponseEntity<String> blockCard(@PathVariable Long cardId){
        cardService.blockCard(cardId);
        return new ResponseEntity("Card was successfully blocked", OK);
    }

    @PostMapping("/balance")
    private ResponseEntity<String> rechargeBalance(@RequestBody Card card){
        String newBalance = cardService.rechargeBalance(card.getCardNumber(), card.getBalance());
        return new ResponseEntity("Balance recharge was done successfully. New Balance is " + newBalance + " USD", OK);
    }

    @GetMapping("/balance/{cardId}")
    private ResponseEntity<String> getBalance(@PathVariable Long cardId){
        String balance = cardService.getBalance(cardId);
        return new ResponseEntity("Card Balance is " + balance + " USD", OK);
    }

}
