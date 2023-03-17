package com.mastercard.takehome.mctakehome.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mastercard.takehome.mctakehome.models.TransactionRequestModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void verifyValidResponseWithValidInput() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .amount(12.22)
                .cardNum(5555999911112222L)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNum").exists())
                .andExpect(content().json("{ 'approved' : true }"));
    }
    @Test
    public void throwErrorWhenAmountIsLessThanZero() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .amount(-1.0)
                .cardNum(5555999911112222L)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(content().json("{ 'errors' : ['Transaction amount cannot be negative'] }"));
        ;
    }

    @Test
    public void throwErrorWhenCardNumberIsMissingDigits() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .amount(55.0)
                .cardNum(0L)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(content().json("{ 'errors' : ['Invalid card number, missing digits'] }"));
        ;
    }

    @Test
    public void throwErrorWhenCardNumberIsTooManyDigits() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .amount(55.0)
                .cardNum(999999999999000066L)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(content().json("{ 'errors' : ['Invalid card number, too many digits'] }"));
        ;
    }

    @Test
    public void throwErrorWhenCardNumberIsTooManyDigitsAndNegativeAmount() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .amount(-10.50)
                .cardNum(999999999999000066L)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(content().json(
                        "{ 'errors' : ['Invalid card number, too many digits', 'Transaction amount cannot be negative'] }"
                ));
        ;
    }

    @Test
    public void throwErrorWhenAmountFieldIsMissing() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .cardNum(1111111111111111L)
                .build();
        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(content().json("{'errors':['Missing transaction amount field'] }"
                ));
    }
    @Test
    public void throwErrorWhenCardNumFieldIsMissing() throws Exception {
        TransactionRequestModel request = TransactionRequestModel.builder()
                .amount(12.33)
                .build();
        mvc.perform(MockMvcRequestBuilders
                        .post("/analyzeTransaction")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(content().json("{'errors':['Missing card number field'] }"
                ));
    }


    private String toJsonString(Object request) {
        return new Gson().toJson(request);
    }

}
