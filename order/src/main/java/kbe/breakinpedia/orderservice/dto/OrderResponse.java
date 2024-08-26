package kbe.breakinpedia.orderservice.dto;

import kbe.breakinpedia.orderservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String customerId;
    private List<Product> products;
    private double totalCost;
    private String currency;
    private String orderStatus;
}
