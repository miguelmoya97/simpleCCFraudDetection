package com.mastercard.takehome.mctakehome.util;

import java.util.List;

public class Util {
    public static int sumTransactions(List<Integer> transactions) {
        return transactions.stream().mapToInt(Integer::intValue).sum();
    }

    public static String obfuscateCardNumber(Long cardNumber) {
        char[] cardNum = cardNumber.toString().toCharArray();
        for (int i = 4; i < 12; i++) {
            cardNum[i] = '*';
        }
        return String.valueOf(cardNum);
    }
}
