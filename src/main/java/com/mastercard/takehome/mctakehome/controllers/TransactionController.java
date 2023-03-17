package com.mastercard.takehome.mctakehome.controllers;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.validators.concrete.LimitValidator;
import com.mastercard.takehome.mctakehome.validators.concrete.OverUseValidator;
import com.mastercard.takehome.mctakehome.validators.concrete.UnderUseValidator;
import com.mastercard.takehome.mctakehome.external.CardCounter;
import com.mastercard.takehome.mctakehome.models.TransactionRequestModel;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
import com.mastercard.takehome.mctakehome.util.Util;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class TransactionController {
    @PostMapping(path="/analyzeTransaction", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseModel> analyzeTransaction(@Valid @RequestBody TransactionRequestModel req) {
        List<Integer> weeklyTransactions = new CardCounter().fetchWeeklyTransactions(req.getCardNum());

        TransactionResponseModel response = TransactionResponseModel.builder()
                .cardNum(req.getCardNum())
                .amount(req.getAmount())
                .isApproved(true)
                .numTransactions(Util.sumTransactions(weeklyTransactions))
                .build();

        if (!createChainValidators().validate(response)) {
            response.setApproved(false);
        }

        log.info("Card Number = {}, Amount Spent = {}, Number of Transactions (Weekly) = {}",
                Util.obfuscateCardNumber(req.getCardNum()),
                req.getAmount(),
                response.getNumTransactions()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Validator createChainValidators() {
        // Set of Validators to determine fraud requirements
        Validator limitVal = new LimitValidator();
        Validator overUseVal = new OverUseValidator();
        Validator underUseVal = new UnderUseValidator();

        limitVal.setNextValidator(overUseVal);
        overUseVal.setNextValidator(underUseVal);

        return limitVal;
    }
}
