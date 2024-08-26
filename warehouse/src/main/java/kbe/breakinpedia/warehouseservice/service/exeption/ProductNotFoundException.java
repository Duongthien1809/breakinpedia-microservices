package kbe.breakinpedia.warehouseservice.service.exeption;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("product not found with " + id);
    }
}
