package com.nexus.service;

import com.nexus.constant.BusinessConstant;
import com.nexus.entity.Card;
import com.nexus.entity.Transaction;
import com.nexus.repository.CardRepository;
import com.nexus.repository.TransactionRepository;
import com.nexus.sample.data.CardData;
import com.nexus.sample.data.TransactionData;
import com.nexus.service.impl.TransactionServiceImpl;
import com.nexus.utils.AppUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void saveTransaction_ShouldStoreTransaction(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setCardNumber(CardData.CARD_NUMBER);
        cardInDB.setBalance(100);
        cardInDB.setActive(true);
        cardInDB.setBlocked(false);
        cardInDB.setFullExpirationDate(AppUtility.addYearsToDate(Calendar.getInstance(), BusinessConstant.CARD_VALIDITY_IN_YEARS));

        when(cardRepository.findByCardNumber(anyString())).thenReturn(cardInDB);

        transactionService.saveTransaction(cardInDB.getCardNumber(), TransactionData.AMOUNT);
        assertThat(cardInDB.getBalance()).isEqualTo(98.0);
    }

    @Test
    void throwExceptionWhenCardNotFoundWhileSavingTransaction(){
        when(cardRepository.findByCardNumber(anyString())).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> transactionService.saveTransaction(CardData.CARD_NUMBER, 2));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void throwExceptionWhenBalanceNotEnoughWhileSavingTransaction(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(100);

        when(cardRepository.findByCardNumber(anyString())).thenReturn(cardInDB);

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.saveTransaction(CardData.CARD_NUMBER, 200));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void throwExceptionWhenCardIsNotActivatedWhileSavingTransaction(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(100);

        when(cardRepository.findByCardNumber(anyString())).thenReturn(cardInDB);

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.saveTransaction(CardData.CARD_NUMBER, 50));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void throwExceptionWhenCardIsBlockedWhileSavingTransaction(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(100);
        cardInDB.setActive(true);

        when(cardRepository.findByCardNumber(anyString())).thenReturn(cardInDB);

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.saveTransaction(CardData.CARD_NUMBER, 50));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void throwExceptionWhenCardIsExpiredWhileSavingTransaction(){
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(100);
        cardInDB.setActive(true);
        cardInDB.setBlocked(false);
        cardInDB.setFullExpirationDate(new Date("14-May-2023"));

        when(cardRepository.findByCardNumber(anyString())).thenReturn(cardInDB);

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.saveTransaction(CardData.CARD_NUMBER, 50));
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void getTransaction_ShouldReturnTransactionFoundInDB(){
        Transaction transaction = TransactionData.getTransactionData();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Transaction transactionInDB = transactionService.getTransaction(TransactionData.TRANSACTION_ID);
        assertThat(transactionInDB.getAmount()).isEqualTo(2);
        assertThat(transactionInDB.getMethod()).isEqualTo("credit");
    }

    @Test
    void throwExceptionWhenBalanceNotEnoughWhileGettingTransaction(){
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> transactionService.getTransaction(TransactionData.TRANSACTION_ID));
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    void cancelTransaction_ShouldCancelTransactionDoneWithin24Hours(){
        Transaction transactionInDB = TransactionData.getTransactionData();
        Card cardInDB = CardData.getSavedCardData();
        cardInDB.setBalance(8);

        when(transactionRepository.findByIdAndCardNumber(anyLong(), anyString())).thenReturn(transactionInDB);
        when(cardRepository.findByCardNumber(CardData.CARD_NUMBER)).thenReturn(cardInDB);

        transactionService.cancelTransaction(TransactionData.TRANSACTION_ID, TransactionData.CARD_NUMBER);
        assertThat(cardInDB.getBalance()).isEqualTo(10);
        assertThat(transactionInDB.getStatus()).isEqualTo(BusinessConstant.TRANSACTION_CANCELED);
    }

    @Test
    void throwExceptionWhenTransactionNotFoundWhileCancelingTransaction(){
        when(transactionRepository.findByIdAndCardNumber(anyLong(), anyString())).thenReturn(null);

        assertThrows(NoSuchElementException.class,
                () -> transactionService.cancelTransaction(TransactionData.TRANSACTION_ID, TransactionData.CARD_NUMBER));
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    void throwExceptionWhenTransactionIsMoreThanADayOldWhileCancelingTransaction(){
        Transaction transactionInDB = TransactionData.getTransactionData();
        transactionInDB.setCreated(new Date("13-May-2023"));

        when(transactionRepository.findByIdAndCardNumber(anyLong(), anyString())).thenReturn(transactionInDB);
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.cancelTransaction(TransactionData.TRANSACTION_ID, TransactionData.CARD_NUMBER));
        verifyNoMoreInteractions(transactionRepository);

    }

}
