package kbe.breakinpedia.orderservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @Id
    private String id;
    private String productName;
    private String description;
    private double price;
    private int counts;
    private String author;
    private String imageUrl;
}
