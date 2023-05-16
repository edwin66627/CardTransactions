package com.nexus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.constant.TransactionMessage;
import com.nexus.dto.CancelTransactionRequestDTO;
import com.nexus.dto.SaveTransactionRequestDTO;
import com.nexus.dto.TransactionDTO;
import com.nexus.entity.Transaction;
import com.nexus.sample.data.TransactionData;
import com.nexus.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private ModelMapper mapper;
    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    void saveTransaction_ShouldSaveAndReturnMessageBack() throws Exception {
        SaveTransactionRequestDTO requestDTO = TransactionData.getTransactionToSave();
        String transactionNumber = TransactionData.TRANSACTION_NUMBER;
        String message = String.format(TransactionMessage.SAVE_TRANSACTION_DONE, transactionNumber);

        when(transactionService.saveTransaction(anyString(), anyDouble())).thenReturn(transactionNumber);

        mvc.perform(post("/transaction/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.jsonMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void getTransaction_ShouldSendTransactionInfoBack() throws Exception {
        Transaction transactionInDB = TransactionData.getTransactionData();
        TransactionDTO transactionDTO = TransactionData.getTransactionDataDTO();

        when(transactionService.getTransaction(anyString())).thenReturn(transactionInDB);
        when(mapper.map(any(), any())).thenReturn(transactionDTO);

        mvc.perform(get("/transaction/{transactionNumber}", TransactionData.TRANSACTION_NUMBER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.method").value("credit"));
    }

    @Test
    void cancelTransaction_ShouldSendCancelMessageBack() throws Exception {
        CancelTransactionRequestDTO requestDTO = TransactionData.getCancelTrasactionData();

        doNothing().when(transactionService).cancelTransaction(anyString(),anyString());

        mvc.perform(post("/transaction/annulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.jsonMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(TransactionMessage.CANCEL_TRANSACTION_DONE));
        }

}
