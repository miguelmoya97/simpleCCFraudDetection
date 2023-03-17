package com.mastercard.takehome.mctakehome.controllers.validators;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.validators.concrete.UnderUseValidator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class UnderUseValidatorTest {
    private Validator underUseValidator;

    @BeforeAll
    public void init() {
        underUseValidator = new UnderUseValidator();
    }

    @Test
    public void whenTheCardIsUsedLessThan35TimesAndBigAmount() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(5000)
                .isApproved(true)
                .numTransactions(4)
                .build();

        boolean validated = underUseValidator.validate(input);
        assertFalse(validated);
    }

    @Test
    public void whenCardIsUsedLessThan35TimesButSmallAmount() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(200)
                .isApproved(true)
                .numTransactions(10)
                .build();

        boolean validated = underUseValidator.validate(input);
        assertTrue(validated);
    }

    @Test
    public void whenCardIsUsedMoreThan35TimesWithAnyAmount() {
        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(20000)
                .isApproved(true)
                .numTransactions(40)
                .build();

        underUseValidator.validate(input);
        assertTrue(input.isApproved());
    }

}
