package com.mastercard.takehome.mctakehome.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardCounter {
    private final String apiEndpoint = "http://www.randomnumberapi.com/api/v1.0/random?min=0&max=12&count=7";
    /*
            ASSUMPTION: A cardnumber (and other parameters) would be required to fetch a clients weekly card usage.
            In this implementation, cardNumber is NOT used, but it probably should be if this microservice was real
             */
    public List<Integer> fetchWeeklyTransactions(Long cardNumber) {


        // The provided API does not work, so I created my own microservice
        List<Integer> transactions = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            transactions.add(new Random().nextInt(13));
        }
        return transactions;
    }

}
