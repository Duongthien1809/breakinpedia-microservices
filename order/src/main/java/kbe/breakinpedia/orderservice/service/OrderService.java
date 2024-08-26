package kbe.breakinpedia.orderservice.service;

import kbe.breakinpedia.orderservice.dto.OrderRequest;
import kbe.breakinpedia.orderservice.dto.OrderResponse;
import kbe.breakinpedia.orderservice.dto.Payment.PaymentRequest;
import kbe.breakinpedia.orderservice.dto.product.ProductRequest;
import kbe.breakinpedia.orderservice.model.Order;
import kbe.breakinpedia.orderservice.model.Product;
import kbe.breakinpedia.orderservice.repository.OrderRepository;
import kbe.breakinpedia.orderservice.service.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderResponse saveOrder(OrderRequest orderRequest) {
        Order order = createOrder(orderRequest);
        orderRepository.insert(order);

        PaymentRequest paymentRequest = createPaymentRequest(order);
        rabbitTemplate.convertAndSend("order-exchange", "OrderRoutingKey", paymentRequest);

        return mapToOrderResponse(order);
    }

    public OrderResponse getOrderByID(String orderId) {
        checkOrderExistByID(orderId);
        return mapToOrderResponse(orderRepository.getOrderById(orderId));
    }

    public void removeProductFromOrderById(String orderId, String productId) {
        checkOrderExistByID(orderId);
        Order order = orderRepository.getOrderById(orderId);
        order.getProducts().removeIf(product -> product.getId().equals(productId));
        updateAndSaveOrder(order);
    }

    public void addProductToOrder(String orderId, ProductRequest productRequest) {
        checkOrderExistByID(orderId);
        Order order = orderRepository.getOrderById(orderId);
        order.addNewProduct(mapToDto(productRequest));
        updateAndSaveOrder(order);
    }

    public void deleteOrder(String orderId) {
        checkOrderExistByID(orderId);
        orderRepository.deleteById(orderId);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
    }

    private double getSum(List<Product> productsList) {
        return productsList.stream()
                .mapToDouble(product -> product.getPrice() * product.getCounts())
                .sum();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .products(order.getProducts())
                .totalCost(order.getTotalCost())
                .currency(order.getCurrency())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private Product mapToDto(ProductRequest productRequest) {
        Product product = new Product();
        product.setPrice(productRequest.getPrice());
        product.setCounts(productRequest.getCounts());
        product.setId(productRequest.getId());
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setAuthor(productRequest.getAuthor());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }

    private Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        List<Product> products = orderRequest.getProductsDto().stream().map(this::mapToDto).collect(Collectors.toList());
        order.setProducts(products);
        order.setCustomerId(orderRequest.getCustomerId());
        order.setTotalCost(getSum(products));
        order.setCurrency(orderRequest.getCurrency());
        order.setOrderStatus("Process");
        order.setCurrency(orderRequest.getCurrency());
        return order;
    }

    private void checkOrderExistByID(String id) {
        if (!orderRepository.existsOrderById(id)) {
            throw new OrderNotFoundException(id);
        }
    }

    private void updateAndSaveOrder(Order order) {
        order.setTotalCost(getSum(order.getProducts()));
        orderRepository.save(order);
    }

    private PaymentRequest createPaymentRequest(Order order) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCurrency(order.getCurrency());
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setTotalCost(order.getTotalCost());
        return paymentRequest;
    }
}
