package kbe.breakinpedia.warehouseservice.repository;

import kbe.breakinpedia.warehouseservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product getProductsById(String id);

    boolean existsProductByProductName(String productName);
}
