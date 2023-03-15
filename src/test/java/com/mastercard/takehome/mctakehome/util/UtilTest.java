package com.mastercard.takehome.mctakehome.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class UtilTest {

    @Test
    public void testSumTransactions() {
        assertEquals(15, Util.sumTransactions(List.of(1, 2, 3, 4, 5)));
    }

    @Test
    public void testObfuscateCardNumber() {
        Long cardNumber = 1234111188883333L;
        assertEquals("1234********3333", Util.obfuscateCardNumber(cardNumber));
    }
}
