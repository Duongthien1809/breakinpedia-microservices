package kbe.breakinpedia.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String paymentID;
    private String paymentStatus;
    private String transactionID;
    private String orderID;
    private double totalCost;
    private String currency;
}
