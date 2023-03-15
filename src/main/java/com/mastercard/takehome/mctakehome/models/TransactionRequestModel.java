package com.mastercard.takehome.mctakehome.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequestModel {
    @NotNull(message = "Missing card number field`")
    @Min(value = 1000000000000000L, message = "Invalid card number, missing digits")
    @Max(value = 9999999999999999L, message = "Invalid card number, too many digits")
    private final Long cardNum;
    @NotNull(message = "Missing transaction amount field")
    @Min(value = 0, message = "Transaction amount cannot be negative")
    private final Double amount;

}
