package Breakdance.KBEProdukt.repository;

import Breakdance.KBEProdukt.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> getProductsByIdIn(List<String> ids);

    Product getProductById(String id);

    Product getProductsByProductName(String productName);

    boolean existsProductByProductName(String productName);

    boolean existsProductById(String id);
}
