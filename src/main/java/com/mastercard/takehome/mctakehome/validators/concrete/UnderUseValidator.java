package com.mastercard.takehome.mctakehome.validators.concrete;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;

public class UnderUseValidator extends Validator {

    public UnderUseValidator() {
        super();
    }

    @Override
    public void process(TransactionResponseModel response) {
        if (response.getNumTransactions() <= 35 && response.getAmount()/ response.getNumTransactions() > 500) {
            response.setApproved(false);
        } else {
            super.process(response);
        }
    }
}
