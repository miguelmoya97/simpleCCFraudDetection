package com.mastercard.takehome.mctakehome.controllers.validators.concrete;

import com.mastercard.takehome.mctakehome.controllers.validators.Validator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;

public class OverUseValidator extends Validator {
    public OverUseValidator() {
        super();
    }

    @Override
    public void process(TransactionResponseModel response) {
        if (response.getNumTransactions() >= 60) {
            response.setApproved(false);
        } else {
            super.process(response);
        }
    }
}
