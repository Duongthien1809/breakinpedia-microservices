package kbe.breakinpedia.orderservice.dto.product;

import kbe.breakinpedia.orderservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListRequest {
    private List<Product> productList;
}
