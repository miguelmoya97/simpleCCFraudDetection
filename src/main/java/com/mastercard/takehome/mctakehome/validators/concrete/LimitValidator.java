package com.mastercard.takehome.mctakehome.validators.concrete;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;

public class LimitValidator extends Validator {
    public LimitValidator() {
        super();
    }

    @Override
    public boolean validate(TransactionResponseModel response) {
        if (response.getAmount() >= 50000) {
            return false;
        } else {
            return super.validate(response);
        }
    }
}
