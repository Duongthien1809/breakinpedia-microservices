package Breakdance.KBEProdukt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCheckResponse {
    private String id;
    private boolean isInProduct;
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal counts;
    private String author;
    private String imageUrl;
}
