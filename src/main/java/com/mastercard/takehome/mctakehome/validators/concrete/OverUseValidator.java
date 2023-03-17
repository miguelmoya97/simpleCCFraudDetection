package com.mastercard.takehome.mctakehome.validators.concrete;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;

public class OverUseValidator extends Validator {
    public OverUseValidator() {
        super();
    }

    @Override
    public boolean validate(TransactionResponseModel response) {
        if (response.getNumTransactions() >= 60) {
            return false;
        } else {
            return super.validate(response);
        }
    }
}
