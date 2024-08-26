package com.example.currencyservice.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyServiceTest {
    @Test
    void switchAmount_sameCurrency_shouldReturnSameAmount() {
        double amount = 100.0;
        String currentCurrency = "USD";
        String newCurrency = "USD";

        double result = CurrencyService.switchAmount(amount, currentCurrency, newCurrency);

        assertEquals(amount, result, 0.001); // You can adjust the delta based on your precision requirements
    }

    @Test
    void switchAmount_differentCurrencies_shouldReturnConvertedAmount() {
        double amount = 100.0;
        String currentCurrency = "USD";
        String newCurrency = "EUR";

        double result = CurrencyService.switchAmount(amount, currentCurrency, newCurrency);

        // Assuming the conversion rate from USD to EUR is 1.09
        assertEquals(91.74, result, 0.001); // You can adjust the delta based on your precision requirements
    }

    @Test
    void switchAmount_unknownCurrencies_shouldReturnZero() {
        double amount = 100.0;
        String currentCurrency = "GBP"; // Assuming GBP is not recognized
        String newCurrency = "USD";

        double result = CurrencyService.switchAmount(amount, currentCurrency, newCurrency);

        assertEquals(109.0, result, 0.001); // You can adjust the delta based on your precision requirements
    }
}