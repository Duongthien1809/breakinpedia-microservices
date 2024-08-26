package kbe.breakinpedia.orderservice.repository;

import kbe.breakinpedia.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OrderRepository extends MongoRepository<Order, String> {
    Order getOrderById(String orderId);

    Boolean existsOrderById(String id);

}
