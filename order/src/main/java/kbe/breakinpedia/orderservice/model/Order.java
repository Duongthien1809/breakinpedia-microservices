package kbe.breakinpedia.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Document(collection = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private String customerId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;
    private double totalCost;
    private String currency;
    private String orderStatus;

    public void addNewProduct(Product product) {
        this.products.add(product);
    }
}
