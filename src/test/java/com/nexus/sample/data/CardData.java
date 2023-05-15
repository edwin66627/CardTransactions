package com.nexus.sample.data;

import com.nexus.dto.ActivateCardRequestDTO;
import com.nexus.dto.CreateCardDTO;
import com.nexus.dto.RechargeBalanceRequestDTO;
import com.nexus.entity.Card;

import javax.swing.plaf.PanelUI;

public class CardData {
    public static final Long CARD_ID = 100152L;
    public static final double AMOUNT = 20;
    public static final String CARD_NUMBER = "1001529311360714";
    public static final Card getCardDataToSave(){
        Card card = new Card();
        card.setFirstName("Alice");
        card.setLastName("Dane");
        card.setType("credit");
        card.setCurrency("USD");
        return card;
    }

    public static final Card getSavedCardData(){
        Card card = new Card();
        card.setId(CARD_ID);
        card.setFirstName("Alice");
        card.setLastName("Dane");
        card.setType("credit");
        card.setCurrency("USD");
        card.setActive(false);
        card.setBlocked(true);
        return card;
    }

    public static final CreateCardDTO getSavedCardDataDTO(){
        CreateCardDTO card = new CreateCardDTO();
        card.setId(1000001L);
        card.setFirstName("Alice");
        card.setLastName("Dane");
        card.setType("credit");
        card.setCurrency("USD");
        return card;
    }

    public static final ActivateCardRequestDTO getActivateCardData(){
        ActivateCardRequestDTO requestDTO = new ActivateCardRequestDTO();
        requestDTO.setCardNumber(CARD_NUMBER);
        return requestDTO;
    }

    public static final RechargeBalanceRequestDTO getRechargeBalanceData(){
        RechargeBalanceRequestDTO requestDTO = new RechargeBalanceRequestDTO();
        requestDTO.setCardNumber(CARD_NUMBER);
        requestDTO.setAmount(AMOUNT);
        return requestDTO;
    }
}
