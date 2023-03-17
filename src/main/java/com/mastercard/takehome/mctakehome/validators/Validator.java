package com.mastercard.takehome.mctakehome.validators;

import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
public abstract class Validator {
    private Validator nextValidator;

    public void setNextValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public void process(TransactionResponseModel response) {
        if (nextValidator != null) {
            nextValidator.process(response);
        }
    }
}
