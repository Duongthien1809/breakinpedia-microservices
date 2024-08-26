package kbe.breakinpedia.warehouseservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Document(collection = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private String id;
    @Column(unique = true)
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal counts;
    private String author;
    private String imageUrl;
}
