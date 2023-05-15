package com.nexus.controller;

import com.nexus.constant.CardMessage;
import com.nexus.dto.ActivateCardRequestDTO;
import com.nexus.dto.CreateCardDTO;
import com.nexus.dto.RechargeBalanceRequestDTO;
import com.nexus.entity.Card;
import com.nexus.entity.HttpResponse;
import com.nexus.exception.ExceptionHandling;
import com.nexus.service.CardService;
import com.nexus.utils.ResponseUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("/card")
public class CardController extends ExceptionHandling {

    private CardService cardService;
    private ModelMapper mapper;
    @Autowired
    public CardController(CardService cardService, ModelMapper mapper) {
        this.cardService = cardService;
        this.mapper = mapper;
    }
    @PostMapping("/create")
    private ResponseEntity<CreateCardDTO> createCard(@Valid @RequestBody CreateCardDTO createCardDTO){
        Card cardSaved = cardService.createCard(mapper.map(createCardDTO, Card.class));
        CreateCardDTO cardDTO = mapper.map(cardSaved, CreateCardDTO.class);
        return ResponseEntity.status(CREATED).body(cardDTO);
    }

    @GetMapping("/{productId}/number")
    private ResponseEntity<Map<String,String>> getCardNumber(@PathVariable Long productId){
        String cardNumber = cardService.generateCardNumber(productId);
        return new ResponseEntity<>(Collections.singletonMap(CardMessage.CARD_NUMBER_FIELD, cardNumber), OK);
    }

    @PostMapping("/enroll")
    private ResponseEntity<HttpResponse> activateCard(@Valid @RequestBody ActivateCardRequestDTO activateCardDTO){
        cardService.activateCard(activateCardDTO.getCardNumber());
        return ResponseUtility.buildResponse(CardMessage.ACTIVATION_DONE, OK);
    }

    @DeleteMapping("/{cardId}")
    private ResponseEntity<HttpResponse> blockCard(@PathVariable Long cardId){
        cardService.blockCard(cardId);
        return ResponseUtility.buildResponse(CardMessage.BLOCK_DONE, OK);
    }

    @PostMapping("/balance")
    private ResponseEntity<HttpResponse> rechargeBalance(@Valid @RequestBody RechargeBalanceRequestDTO rechargeDTO){
        String newBalance = cardService.rechargeBalance(rechargeDTO.getCardNumber(), rechargeDTO.getAmount());
        return ResponseUtility.buildResponse(String.format(CardMessage.RECHARGE_DONE, newBalance), OK);
    }

    @GetMapping("/balance/{cardId}")
    private ResponseEntity<HttpResponse> getBalance(@PathVariable Long cardId){
        String balance = cardService.getBalance(cardId);
        return ResponseUtility.buildResponse(String.format(CardMessage.CARD_BALANCE, balance), OK);
    }

}
