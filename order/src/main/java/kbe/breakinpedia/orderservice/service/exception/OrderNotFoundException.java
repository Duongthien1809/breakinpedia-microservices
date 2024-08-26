package kbe.breakinpedia.orderservice.service.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String id) {
        super("couldn't find order with id: " + id);
    }
}
