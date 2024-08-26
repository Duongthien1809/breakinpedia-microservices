package kbe.breakinpedia.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRequest {
    private String currentCurrency;
    private String newCurrency;
    private double amount;
}
