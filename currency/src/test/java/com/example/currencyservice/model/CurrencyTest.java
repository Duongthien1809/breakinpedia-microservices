package com.example.currencyservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTest {
    @Test
    public void testSwitchAmount_sameCurrency_shouldReturnSameAmount() {
        double amount = 50.0;
        Currency currency = Currency.EUR;

        double result = Currency.switchAmount(amount, currency, currency);

        assertEquals(amount, result, 0.001);
    }

    @Test
    public void testSwitchAmount_differentCurrency_shouldReturnConvertedAmount() {
        double amount = 50.0;
        Currency currentCurrency = Currency.USD;
        Currency newCurrency = Currency.EUR;

        double result = Currency.switchAmount(amount, currentCurrency, newCurrency);
        System.out.println(result);

        //assertEquals(45.87, result, 0.001);
    }

    @Test
    public void testSwitchAmount_convertToEUR_shouldReturnConvertedAmount() {
        double amount = 50.0;
        Currency currentCurrency = Currency.USD;
        Currency newCurrency = Currency.EUR;

        double result = Currency.switchAmount(amount, currentCurrency, newCurrency);

        assertEquals(45.87, result, 0.001);
    }

    @Test
    public void testSwitchAmount_convertFromEUR_shouldReturnConvertedAmount() {
        double amount = 50.0;
        Currency currentCurrency = Currency.EUR;
        Currency newCurrency = Currency.USD;

        double result = Currency.switchAmount(amount, currentCurrency, newCurrency);

        assertEquals(54.5, result, 0.001);
    }

    @Test
    public void testRoundToTwoDecimals() {
        double value = 123.456;

        double result = Currency.roundToTwoDecimals(value);

        assertEquals(123.46, result, 0.001);
    }
}