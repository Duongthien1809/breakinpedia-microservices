package Breakdance.KBEProdukt.controller;

import Breakdance.KBEProdukt.dto.ProductCheckResponse;
import Breakdance.KBEProdukt.dto.ProductRequest;
import Breakdance.KBEProdukt.dto.ProductResponse;
import Breakdance.KBEProdukt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * create new product
     *
     * @param productRequest: add required data
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    /**
     * get all product in database
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCheckResponse> getProductsUsingParams(@RequestParam List<String> id) {
        return productService.getProductsByParams(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteProductById(@PathVariable String id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponse updateProductById(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        return productService.updateProductById(id, productRequest);
    }

    @GetMapping("/byName/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductByProductName(@PathVariable String productName) {
        return productService.getProductByProductName(productName);
    }
}
