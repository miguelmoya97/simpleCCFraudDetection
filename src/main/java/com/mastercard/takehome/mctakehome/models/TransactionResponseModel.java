package com.mastercard.takehome.mctakehome.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionResponseModel {
    private final long cardNum;
    private final double amount;
    private boolean isApproved;
    private final int numTransactions;
}
