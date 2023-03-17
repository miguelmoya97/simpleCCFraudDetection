package com.mastercard.takehome.mctakehome.validators.concrete;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;

public class LimitValidator extends Validator {
    public LimitValidator() {
        super();
    }

    @Override
    public void process(TransactionResponseModel response) {
        if (response.getAmount() >= 50000) {
            response.setApproved(false);
        } else {
            super.process(response);
        }
    }
}
