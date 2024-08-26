package Breakdance.KBEProdukt.service;

import Breakdance.KBEProdukt.dto.ProductCheckResponse;
import Breakdance.KBEProdukt.dto.ProductRequest;
import Breakdance.KBEProdukt.dto.ProductResponse;
import Breakdance.KBEProdukt.model.Product;
import Breakdance.KBEProdukt.repository.ProductRepository;
import Breakdance.KBEProdukt.service.exeption.InvalidRequestException;
import Breakdance.KBEProdukt.service.exeption.ProductNameAlreadyExistException;
import Breakdance.KBEProdukt.service.exeption.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        if (productRequest == null) throw new InvalidRequestException();
        if (productRepository.existsProductByProductName(productRequest.getProductName())) {
            throw new ProductNameAlreadyExistException(productRequest.getProductName());
        }

        Product product = createProductInstance(productRequest);
        Product savedProduct = productRepository.insert(product);

        logProductInserted(savedProduct);

        return maptoProductResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::maptoProductResponse).toList();
    }

    public ProductResponse getProductById(String id) {
        if (id.isEmpty()) throw new InvalidRequestException();
        if (!productRepository.existsProductById(id)) throw new ProductNotFoundException(id);

        return maptoProductResponse(productRepository.getProductById(id));
    }

    public ProductResponse getProductByProductName(String productName) {
        if (productName.isEmpty()) throw new InvalidRequestException();
        return maptoProductResponse(productRepository.getProductsByProductName(productName));
    }

    public List<ProductCheckResponse> getProductsByParams(List<String> ids) {
        if (ids == null) throw new InvalidRequestException();
        log.info("checking Product!!");
        return productRepository.getProductsByIdIn(ids).stream().map(product -> ProductCheckResponse.builder().id(product.getId()).productName(product.getProductName()).isInProduct(productRepository.existsProductById(product.getId())).author(product.getAuthor()).price(product.getPrice()).counts(product.getCounts()).imageUrl(product.getImageUrl()).build()).toList();
    }

    public String deleteProductById(String id) {
        if (productRepository.existsProductById(id)) {
            productRepository.deleteById(id);
            return "product with given id: " + id + " is deleted!";
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    public ProductResponse updateProductById(String id, ProductRequest productRequest) {
        if (!productRepository.existsProductById(id)) {
            throw new ProductNotFoundException(id);
        }

        if (productRequest == null) {
            throw new InvalidRequestException();
        }

        Product existingProduct = productRepository.getProductById(id);

        String productName = (productRequest.getProductName() != null) ? productRequest.getProductName() : existingProduct.getProductName();
        String description = (productRequest.getDescription() != null) ? productRequest.getDescription() : existingProduct.getDescription();
        BigDecimal price = (productRequest.getPrice() != null) ? productRequest.getPrice() : existingProduct.getPrice();
        String author = (productRequest.getAuthor() != null) ? productRequest.getAuthor() : existingProduct.getAuthor();
        String imageUrl = (productRequest.getImageUrl() != null) ? productRequest.getImageUrl() : existingProduct.getImageUrl();
        BigDecimal counts = (productRequest.getCounts() != null) ? productRequest.getCounts() : existingProduct.getCounts();

        //updating data
        existingProduct.setProductName(productName);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setCounts(counts);
        existingProduct.setAuthor(author);
        existingProduct.setImageUrl(imageUrl);

        productRepository.save(existingProduct);

        return maptoProductResponse(productRepository.getProductById(id));
    }

    /***************************************************/
    private ProductResponse maptoProductResponse(Product product) {
        if (product == null) throw new InvalidRequestException();
        return ProductResponse.builder().id(product.getId()).productName(product.getProductName()).description(product.getDescription()).price(product.getPrice()).counts(product.getCounts()).author(product.getAuthor()).imageUrl(product.getImageUrl()).build();
    }

    private Product createProductInstance(ProductRequest productRequest) {
        if (productRequest == null) throw new InvalidRequestException();
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCounts(productRequest.getCounts());
        product.setProductName(productRequest.getProductName());
        product.setAuthor(productRequest.getAuthor());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }

    private void logProductInserted(Product product) {
        if (product == null) throw new InvalidRequestException();
        log.info("Product: {}, ID: {} has been inserted!", product.getProductName(), product.getId());
    }
}
