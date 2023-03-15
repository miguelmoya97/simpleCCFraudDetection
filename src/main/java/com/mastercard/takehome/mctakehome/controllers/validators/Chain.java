package com.mastercard.takehome.mctakehome.controllers.validators;

import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Chain {
    private final Validator validator;

    public void process(TransactionResponseModel response) {
        validator.process(response);
    }
}
