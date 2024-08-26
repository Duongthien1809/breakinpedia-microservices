package com.example.currencyservice.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public enum Currency {
    EUR(1.0), USD(1.09), VND(25712); // EUR is the base currency

    private final double toEUR;

    Currency(double toEUR) {
        this.toEUR = toEUR;
    }

    public static double switchAmount(double currentAmount, Currency currentCurrency, Currency newCurrency) {
        if (currentCurrency == newCurrency) {
            return currentAmount;
        }

        if (currentCurrency == EUR) {
            return roundToTwoDecimals(currentAmount * newCurrency.toEUR);
        }

        return roundToTwoDecimals(currentCurrencyNotEUR(currentAmount / currentCurrency.toEUR, newCurrency));
    }

    private static double currentCurrencyNotEUR(double amountInEUR, Currency newCurrency) {
        if (newCurrency == EUR) {
            return amountInEUR;
        }

        return roundToTwoDecimals(amountInEUR * newCurrency.toEUR);
    }

    public static double roundToTwoDecimals(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        return Double.parseDouble(df.format(value));
    }
}
