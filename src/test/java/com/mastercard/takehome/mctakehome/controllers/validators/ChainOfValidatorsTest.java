package com.mastercard.takehome.mctakehome.controllers.validators;

import com.mastercard.takehome.mctakehome.validators.Validator;
import com.mastercard.takehome.mctakehome.validators.concrete.LimitValidator;
import com.mastercard.takehome.mctakehome.validators.concrete.OverUseValidator;
import com.mastercard.takehome.mctakehome.validators.concrete.UnderUseValidator;
import com.mastercard.takehome.mctakehome.models.TransactionResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChainOfValidatorsTest {
    private Validator limitValidator;
    private Validator overUseValidator;
    private Validator underUseValidator;

    @BeforeAll
    public void init() {
        limitValidator = mock(LimitValidator.class, CALLS_REAL_METHODS);
        overUseValidator = mock(OverUseValidator.class, CALLS_REAL_METHODS);
        underUseValidator = mock(UnderUseValidator.class, CALLS_REAL_METHODS);

        limitValidator.setNextValidator(overUseValidator);
        overUseValidator.setNextValidator(underUseValidator);
    }

    @Test
    public void verifyAllValidatorsAreExecutedWithValidInput() {

        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(10000)
                .isApproved(true)
                .numTransactions(40)
                .build();

        boolean validated = limitValidator.validate(input);

        verify(limitValidator, atLeastOnce()).validate(input);
        verify(overUseValidator, atLeastOnce()).validate(input);
        verify(underUseValidator, atLeastOnce()).validate(input);

        assertTrue(validated);
    }

    @Test
    public void verifyFalseResponseWhenLimitAmountIsTriggered() {

        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(50000)
                .isApproved(true)
                .numTransactions(40)
                .build();

        boolean validated = limitValidator.validate(input);

        verify(limitValidator, atLeastOnce()).validate(input);
        verify(overUseValidator, never()).validate(input);
        verify(underUseValidator, never()).validate(input);

        assertFalse(validated);
    }

    @Test
    public void verifyFalseResponseWhenOverUseIsTriggered() {

        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(5000)
                .isApproved(true)
                .numTransactions(61)
                .build();

        boolean validated = limitValidator.validate(input);

        verify(limitValidator, atLeastOnce()).validate(input);
        verify(overUseValidator,atLeastOnce()).validate(input);
        verify(underUseValidator, never()).validate(input);

        assertFalse(validated);
    }

    @Test
    public void verifyFalseResponseWhenUnderUseIsTriggered() {

        TransactionResponseModel input = TransactionResponseModel.builder()
                .amount(5000)
                .isApproved(true)
                .numTransactions(1)
                .build();

        boolean validated = limitValidator.validate(input);

        verify(limitValidator, atLeastOnce()).validate(input);
        verify(overUseValidator,atLeastOnce()).validate(input);
        verify(underUseValidator, atLeastOnce()).validate(input);

        assertFalse(validated);
    }
}
