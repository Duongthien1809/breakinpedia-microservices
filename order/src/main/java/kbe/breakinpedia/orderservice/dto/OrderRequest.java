package kbe.breakinpedia.orderservice.dto;

import kbe.breakinpedia.orderservice.dto.product.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String customerId;
    private List<ProductRequest> productsDto;
    private String currency;
}
