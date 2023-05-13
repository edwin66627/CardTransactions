package com.nexus.controller;

import com.nexus.entity.Card;
import com.nexus.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(cardSaved, OK);
    }
}
