package com.example.currencyservice.service;

import com.example.currencyservice.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.currencyservice.model.Currency.*;

@Service
@Slf4j
public class CurrencyService {

    public static Double switchAmount(double currentAmount, String currentCurrency, String newCurrency) {
        Currency tmpCurrent;
        Currency tmpNew;

        tmpCurrent = matchStringToCurrency(currentCurrency);
        tmpNew = matchStringToCurrency(newCurrency);
        log.info("change amount from {} to {}", currentCurrency, newCurrency);

        return Currency.switchAmount(currentAmount, tmpCurrent, tmpNew);
    }

    private static Currency matchStringToCurrency(String currency) {
        return switch (currency) {
            case "USD" -> USD;
            case "VND" -> VND;
            default -> EUR;
        };
    }

}
