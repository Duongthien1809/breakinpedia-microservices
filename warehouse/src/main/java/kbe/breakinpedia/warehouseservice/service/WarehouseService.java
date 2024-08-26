package kbe.breakinpedia.warehouseservice.service;

import kbe.breakinpedia.warehouseservice.config.CSVParser;
import kbe.breakinpedia.warehouseservice.dto.ProductResponse;
import kbe.breakinpedia.warehouseservice.model.Product;
import kbe.breakinpedia.warehouseservice.repository.ProductRepository;
import kbe.breakinpedia.warehouseservice.service.exeption.InvalidRequestException;
import kbe.breakinpedia.warehouseservice.service.exeption.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseService {
    private final ProductRepository productRepository;

    @Value("classpath:products.csv")
    private Resource productCSV;

    @PostConstruct
    public void init() {
//        productRepository.deleteAll();
//        log.info("clean database");
        InputStream componentsStream = null;
        try {
            componentsStream = productCSV.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.readCSV(componentsStream);
        log.info("products inserted");
    }


    public void readCSV(InputStream inputStream) {
        List<Product> components;
        components = CSVParser.parse(new InputStreamReader(inputStream));
        List<Product> canInsertProduct = new ArrayList<>();
        for (Product product : components) {
            if (!productRepository.existsProductByProductName(product.getProductName())) {
                canInsertProduct.add(product);
            }
        }
        productRepository.saveAll(canInsertProduct);
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::maptoProductResponse).toList();
    }

    public ProductResponse getProductById(String id) {
        if (id.isEmpty()) throw new InvalidRequestException();
        return maptoProductResponse(productRepository.getProductsById(id));
    }

    /**
     * reduct product counts by adding product id
     */
    public void reduceProductCountByProductId(String productId, BigDecimal counts) {
        // Check if the product exists in the database
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found for ID: {}", productId);
                    return new ProductNotFoundException("Product not found for ID: " + productId);
                });

        // Product found, reduce the inventory
        BigDecimal updatedCount = existingProduct.getCounts().subtract(counts);
        if (updatedCount.intValue() >= 0) {
            existingProduct.setCounts(updatedCount);
            // Update product
            productRepository.save(existingProduct);
            log.info("Reduced counts for product {} by {}: New count is {}", productId, counts, updatedCount);
        } else {
            log.error("not enough product to order");
        }

    }

    private ProductResponse maptoProductResponse(Product product) {
        return ProductResponse.builder().id(product.getId()).productName(product.getProductName()).description(product.getDescription()).price(product.getPrice()).counts(product.getCounts()).author(product.getAuthor()).imageUrl(product.getImageUrl()).build();
    }

}
