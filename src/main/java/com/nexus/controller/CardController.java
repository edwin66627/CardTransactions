package com.nexus.controller;

import com.nexus.entity.Card;
import com.nexus.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("/card")
public class CardController {

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
    private ResponseEntity<?> activateCard(@RequestBody Card card){
        cardService.activateCard(card.getcardNumber());
        return new ResponseEntity("Card was successfully activated", OK);
    }

    @DeleteMapping("/{cardId}")
    private ResponseEntity<?> blockCard(@PathVariable Long cardId){
        cardService.blockCard(cardId);
        return new ResponseEntity("Card was successfully blocked", OK);
    }

}
