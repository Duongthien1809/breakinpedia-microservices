package Breakdance.KBEProdukt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * all required Data need to add to Product
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal counts;
    private String author;
    private String imageUrl;
}
