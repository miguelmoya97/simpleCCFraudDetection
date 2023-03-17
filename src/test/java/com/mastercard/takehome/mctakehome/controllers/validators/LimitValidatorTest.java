package com.mastercard.takehome.mctakehome.controllers.validators;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.validators.concrete.LimitValidator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LimitValidatorTest {
    private Validator limitValidator;
    @BeforeAll
    public void init() {
        limitValidator = new LimitValidator();
    }
    @Test
    public void whenTransactionAmountIsOverLimit() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(50000)
                .isApproved(true)
                .build();
        limitValidator.process(input);
        assertFalse(input.isApproved());
    }

    @Test
    public void whenTransactionAmountIsUnderLimit() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(500)
                .isApproved(true)
                .build();
        limitValidator.process(input);
        assertTrue(input.isApproved());
    }
}
