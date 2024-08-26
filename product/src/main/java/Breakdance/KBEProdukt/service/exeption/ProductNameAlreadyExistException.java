package Breakdance.KBEProdukt.service.exeption;

public class ProductNameAlreadyExistException extends RuntimeException {
    public ProductNameAlreadyExistException(String productName) {
        super("product with name: " + productName + " is already exist!");
    }
}
