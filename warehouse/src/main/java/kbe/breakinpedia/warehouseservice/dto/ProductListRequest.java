package kbe.breakinpedia.warehouseservice.dto;

import kbe.breakinpedia.warehouseservice.model.Product;
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
