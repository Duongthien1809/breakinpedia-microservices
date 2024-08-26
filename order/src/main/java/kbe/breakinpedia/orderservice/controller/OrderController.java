package kbe.breakinpedia.orderservice.controller;

import kbe.breakinpedia.orderservice.dto.OrderRequest;
import kbe.breakinpedia.orderservice.dto.OrderResponse;
import kbe.breakinpedia.orderservice.dto.product.ProductRequest;
import kbe.breakinpedia.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor

public class OrderController {
    @Autowired
    private final OrderService orderService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse saveOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.saveOrder(orderRequest);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return "Order with " + orderId + " is deleted";
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable String orderId) {
        return orderService.getOrderByID(orderId);
    }

    @DeleteMapping("/{orderId}/product/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String removeProductFromOrder(@PathVariable String orderId, @PathVariable String productId) {
        orderService.removeProductFromOrderById(orderId, productId);
        return "Product with ID " + productId + " was removed from order with ID " + orderId;
    }

    @PostMapping("/{orderId}/product")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String addProductToOrder(@PathVariable String orderId, @RequestBody ProductRequest productRequest) {
        orderService.addProductToOrder(orderId, productRequest);
        return "Product with ID " + productRequest.getId() + " was added to order with ID " + orderId;
    }


}
