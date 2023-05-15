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

        doNothing().when(transactionService).saveTransaction(anyString(), anyDouble());

        mvc.perform(post("/transaction/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.jsonMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(TransactionMessage.SAVE_TRANSACTION_DONE));
    }

    @Test
    void getTransaction_ShouldSendTransactionInfoBack() throws Exception {
        Transaction transactionInDB = TransactionData.getTransactionData();
        TransactionDTO transactionDTO = TransactionData.getTransactionDataDTO();

        when(transactionService.getTransaction(anyLong())).thenReturn(transactionInDB);
        when(mapper.map(any(), any())).thenReturn(transactionDTO);

        mvc.perform(get("/transaction/{transactionId}", TransactionData.TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.method").value("credit"));
    }

    @Test
    void cancelTransaction_ShouldSendCancelMessageBack() throws Exception {
        CancelTransactionRequestDTO requestDTO = TransactionData.getCancelTrasactionData();

        doNothing().when(transactionService).cancelTransaction(anyLong(),anyString());

        mvc.perform(post("/transaction/annulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.jsonMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(TransactionMessage.CANCEL_TRANSACTION_DONE));
        }

}
