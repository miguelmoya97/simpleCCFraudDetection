package com.mastercard.takehome.mctakehome.validators;

import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
public abstract class Validator {
    private Validator nextValidator;

    public void setNextValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public boolean validate(TransactionResponseModel response) {
        if (nextValidator != null) {
            return nextValidator.validate(response);
        }
        return true;
    }
}
