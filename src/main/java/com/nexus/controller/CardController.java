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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Create a Card",
        description = "Create a card by default inactivated and blocked with basic information")
    private ResponseEntity<CreateCardDTO> createCard(
            @Parameter(description = "Card's mandatory fields are: type, firstName, lastName and currency. " +
                    "Valid type values are: debit and credit. Valid currency, for now, is USD.")
            @Valid @RequestBody CreateCardDTO createCardDTO){
        Card cardSaved = cardService.createCard(mapper.map(createCardDTO, Card.class));
        CreateCardDTO cardDTO = mapper.map(cardSaved, CreateCardDTO.class);
        return ResponseEntity.status(CREATED).body(cardDTO);

    }

    @GetMapping("/{productId}/number")
    @Operation(summary = "Generate Card number", description = "Generate card number of an existing Card")
    private ResponseEntity<Map<String,String>> getCardNumber(
            @Parameter(description = "Card id field. It is used to fetch the card and form the first 6 characters" +
                    " of the Card number")
            @PathVariable Long productId){
        String cardNumber = cardService.generateCardNumber(productId);
        return new ResponseEntity<>(Collections.singletonMap(CardMessage.CARD_NUMBER_FIELD, cardNumber), OK);
    }

    @PostMapping("/enroll")
    @Operation(summary = "Activate a Card", description = "Activate an existing Card")
    private ResponseEntity<HttpResponse> activateCard(
            @Parameter(description = "Card number of the Card to be activated")
            @Valid @RequestBody ActivateCardRequestDTO activateCardDTO){
        cardService.activateCard(activateCardDTO.getCardNumber());
        return ResponseUtility.buildResponse(CardMessage.ACTIVATION_DONE, OK);
    }

    @DeleteMapping("/{cardId}")
    @Operation(summary = "Block a Card", description = "Block an existing Card")
    private ResponseEntity<HttpResponse> blockCard(
            @Parameter(description = "Card id field used to fetch a Card and block it")
            @PathVariable Long cardId){
        cardService.blockCard(cardId);
        return ResponseUtility.buildResponse(CardMessage.BLOCK_DONE, OK);
    }

    @PostMapping("/balance")
    @Operation(summary = "Recharge Card balance", description = "Recharge balance of an existing card")
    private ResponseEntity<HttpResponse> rechargeBalance(
            @Parameter(description = "Card number to fetch card to recharge. Amount to recharge. " +
                    "Valid amount must be equal or greater than 1 USD")
            @Valid @RequestBody RechargeBalanceRequestDTO rechargeDTO){
        String newBalance = cardService.rechargeBalance(rechargeDTO.getCardNumber(), rechargeDTO.getAmount());
        return ResponseUtility.buildResponse(String.format(CardMessage.RECHARGE_DONE, newBalance), OK);
    }

    @GetMapping("/balance/{cardId}")
    @Operation(summary = "Get Balance", description = "Get the current balance of an existing Card")
    private ResponseEntity<HttpResponse> getBalance(
            @Parameter(description = "Card id field used to fetch a Card balance")
            @PathVariable Long cardId){
        String balance = cardService.getBalance(cardId);
        return ResponseUtility.buildResponse(String.format(CardMessage.CARD_BALANCE, balance), OK);
    }

}
