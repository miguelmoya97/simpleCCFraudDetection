package com.mastercard.takehome.mctakehome.controllers.validators;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.validators.concrete.OverUseValidator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class OverUseValidatorTest {
    private Validator overUseValidator;

    @BeforeAll
    public void init() {
        overUseValidator = new OverUseValidator();
    }

    @Test
    public void whenNumTransactionsExceedsTheLimit() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(10000)
                .isApproved(true)
                .numTransactions(60)
                .build();

        overUseValidator.process(input);
        assertFalse(input.isApproved());
    }

    @Test
    public void whenNumTransactionsDoesNotTheLimit() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(10000)
                .isApproved(true)
                .numTransactions(59)
                .build();

        overUseValidator.process(input);
        assertTrue(input.isApproved());
    }
}
