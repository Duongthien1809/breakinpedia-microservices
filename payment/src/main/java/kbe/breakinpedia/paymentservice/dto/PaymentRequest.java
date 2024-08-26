package kbe.breakinpedia.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest {
    private String orderId;
    private double totalCost;
    private String currency;
}
