package com.nexus.service;

import com.nexus.entity.Card;
import com.nexus.repository.CardRepository;
import com.nexus.sample.data.CardData;
import com.nexus.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void createCard_ShouldReturnCardSaved(){
        Card card = CardData.getCardDataToSave();

        when(cardRepository.save(card)).thenReturn(card);

        Card cardToSendBack = cardService.createCard(card);
        assertThat(cardToSendBack.getExpirationDate()).isEqualTo("5/2026");
        assertThat(cardToSendBack.isActive()).isEqualTo(false);
        assertThat(cardToSendBack.isBlocked()).isEqualTo(true);
    }

    @Test
    void generateCardNumber_ShouldReturn16DigitNumber(){
        Card cardInDB = CardData.getSavedCardData();

        when(cardRepository.findById(CardData.CARD_ID)).thenReturn(Optional.of(cardInDB));
        when(cardRepository.save(any())).thenReturn(any());

        String generatedCardNumber = cardService.generateCardNumber(CardData.CARD_ID);
        assertThat(generatedCardNumber).isNotEmpty();
        assertThat(generatedCardNumber).contains(cardInDB.getId().toString());
        assertEquals(generatedCardNumber.length(), 16);
    }

    @Test
    void throwExceptionWhenCardNotFoundWhileGeneratingCardNumber(){
        when(cardRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> cardService.generateCardNumber(CardData.CARD_ID));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void throwExceptionWhenCardAlreadyHasNumberAssigned(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setCardNumber(CardData.CARD_NUMBER);

        when(cardRepository.findById(CardData.CARD_ID)).thenReturn(Optional.of(cardInDB));

        assertThrows(IllegalArgumentException.class,
                () -> cardService.generateCardNumber(CardData.CARD_ID));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void activateCard_ShouldActivateAndUnblockCardFoundInDB(){
        Card cardInDB = CardData.getSavedCardData();

        when(cardRepository.findByCardNumber(CardData.CARD_NUMBER)).thenReturn(cardInDB);
        when(cardRepository.save(any())).thenReturn(any());

        cardService.activateCard(CardData.CARD_NUMBER);
        assertTrue(cardInDB.isActive());
        assertFalse(cardInDB.isBlocked());
    }

    @Test
    void throwExceptionWhenCardNotFoundWhileActivatingCard(){
        when(cardRepository.findByCardNumber(anyString())).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> cardService.activateCard(CardData.CARD_NUMBER));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void blockCard_ShouldBlockCardFoundInDB(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBlocked(false);

        when(cardRepository.findById(CardData.CARD_ID)).thenReturn(Optional.of(cardInDB));
        when(cardRepository.save(any())).thenReturn(any());

        cardService.blockCard(CardData.CARD_ID);
        assertTrue(cardInDB.isBlocked());
    }

    @Test
    void throwExceptionWhenCardNotFoundWhileBlockingCard(){
        when(cardRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> cardService.blockCard(CardData.CARD_ID));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void rechargeBalance_ShouldSendNewBalanceBack(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(20.2);

        when(cardRepository.findByCardNumber(CardData.CARD_NUMBER)).thenReturn(cardInDB);
        when(cardRepository.save(any())).thenReturn(any());

        String newBalance = cardService.rechargeBalance(CardData.CARD_NUMBER, 19.8);
        assertEquals(newBalance, "40,00");
    }

    @Test
    void throwExceptionWhenCardNotFoundWhileRechargingCard(){
        when(cardRepository.findByCardNumber(anyString())).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> cardService.rechargeBalance(CardData.CARD_NUMBER, 12.2));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void throwExceptionWhenNotMinimumAmountToRechargeCard(){
        Card cardInDB = CardData.getSavedCardData();

        when(cardRepository.findByCardNumber(CardData.CARD_NUMBER)).thenReturn(cardInDB);

        assertThrows(IllegalArgumentException.class,
                () -> cardService.rechargeBalance(CardData.CARD_NUMBER, 0.2));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void getBalance_ShouldReturnBalanceOfCardFoundInDB(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(100.78);

        when(cardRepository.findById(CardData.CARD_ID)).thenReturn(Optional.of(cardInDB));
        String currentBalance = cardService.getBalance(CardData.CARD_ID);
        assertEquals(currentBalance, "100,78");
    }

    @Test
    void throwExceptionWhenCardNotFoundWhileGettingCardBalance(){
        when(cardRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> cardService.getBalance(CardData.CARD_ID));
        verifyNoMoreInteractions(cardRepository);
    }

}
