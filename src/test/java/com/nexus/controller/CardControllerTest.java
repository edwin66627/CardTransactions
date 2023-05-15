package com.nexus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.constant.CardMessage;
import com.nexus.dto.ActivateCardRequestDTO;
import com.nexus.dto.CreateCardDTO;
import com.nexus.dto.RechargeBalanceRequestDTO;
import com.nexus.entity.Card;
import com.nexus.sample.data.CardData;
import com.nexus.service.CardService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CardService cardService;
    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCard_ShouldReturnCardSaved() throws Exception {
        Card cardToSave = CardData.getCardDataToSave();
        Card cardSaved = CardData.getSavedCardData();
        CreateCardDTO cardSavedDTO = CardData.getSavedCardDataDTO();

        when(mapper.map(any(), any())).thenReturn(cardToSave,cardSavedDTO);
        when(cardService.createCard(any())).thenReturn(cardSaved);

        MockHttpServletRequestBuilder mockRequest = post("/card/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(cardSaved));

        mvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.type").value("credit"));
    }

    @Test
    void getCardNumber_ShouldReturnGeneratedCardNUmber() throws Exception {
        String cardNUmber = CardData.CARD_NUMBER;
        Long cardId = CardData.CARD_ID;
        when(cardService.generateCardNumber(anyLong())).thenReturn(cardNUmber);

        mvc.perform(get("/card/{productId}/number", cardId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").isNotEmpty())
                .andExpect(jsonPath("$.cardNumber").value(cardNUmber))
                .andExpect(jsonPath("$.cardNumber", containsString(cardId.toString())));
    }

    @Test
    void activateCard_ShouldSendActivationMessageBack() throws Exception {
        ActivateCardRequestDTO requestDTO = CardData.getActivateCardData();

        doNothing().when(cardService).activateCard(anyString());

        mvc.perform(post("/card/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(CardMessage.ACTIVATION_DONE));
    }

    @Test
    void blockCard_ShouldSendBlockMessageBack() throws Exception {
        doNothing().when(cardService).blockCard(anyLong());

        mvc.perform(delete("/card/{cardId}", CardData.CARD_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(CardMessage.BLOCK_DONE));

    }

    @Test
    void rechargeBalance_ShouldSendNewBalanceBack() throws Exception {
        RechargeBalanceRequestDTO requestDTO = CardData.getRechargeBalanceData();
        String newBalanceMessage = String.format(CardMessage.RECHARGE_DONE, CardData.AMOUNT);
        String newBalance = String.valueOf(CardData.AMOUNT);

        when(cardService.rechargeBalance(anyString(), anyDouble())).thenReturn(newBalance);

        mvc.perform(post("/card/balance", CardData.CARD_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(newBalanceMessage));
    }

    @Test
    void getBalance_ShouldReturnCurrentBalance() throws Exception {
        String currentBalance = String.valueOf(CardData.AMOUNT);
        String currentBalanceMessage = String.format(CardMessage.CARD_BALANCE, currentBalance);

        when(cardService.getBalance(anyLong())).thenReturn(currentBalance);

        mvc.perform(get("/card/balance/{cardId}", CardData.CARD_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(currentBalanceMessage));
    }



}
