package kbe.breakinpedia.orderservice.service;

import kbe.breakinpedia.orderservice.dto.OrderRequest;
import kbe.breakinpedia.orderservice.dto.OrderResponse;
import kbe.breakinpedia.orderservice.dto.Payment.PaymentRequest;
import kbe.breakinpedia.orderservice.dto.product.ProductRequest;
import kbe.breakinpedia.orderservice.model.Order;
import kbe.breakinpedia.orderservice.model.Product;
import kbe.breakinpedia.orderservice.repository.OrderRepository;
import kbe.breakinpedia.orderservice.service.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrder() {
        // Arrange
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId("customer123");
        orderRequest.setCurrency("USD");
        orderRequest.setProductsDto(Collections.singletonList(new ProductRequest()));

        // Act
        orderService.saveOrder(orderRequest);

        // Assert
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).insert(orderCaptor.capture());
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(PaymentRequest.class));

        Order savedOrder = orderCaptor.getValue();
        assertNotNull(savedOrder);
        assertEquals("customer123", savedOrder.getCustomerId());
        assertEquals("USD", savedOrder.getCurrency());
        assertEquals("Process", savedOrder.getOrderStatus());
    }

    @Test
    void getOrderByID_OrderExists_ReturnsOrderResponse() {
        // Arrange
        Order existingOrder = new Order();
        existingOrder.setId("orderId123");
        when(orderRepository.getOrderById("orderId123")).thenReturn(existingOrder);
        when(orderRepository.existsOrderById("orderId123")).thenReturn(true);

        // Act
        OrderResponse orderResponse = orderService.getOrderByID("orderId123");

        // Assert
        assertNotNull(orderResponse);
        assertEquals("orderId123", orderResponse.getOrderId());
    }

    @Test
    void getOrderByID_OrderDoesNotExist_ThrowsOrderNotFoundException() {
        // Arrange
        when(orderRepository.getOrderById("nonExistentOrderId")).thenReturn(null);

        // Act and Assert
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderByID("nonExistentOrderId"));
    }

    @Test
    void removeProductFromOrderById_OrderExists_ProductRemovedAndOrderUpdated() {
        // Arrange
        Order existingOrder = new Order();
        existingOrder.setId("orderId123");
        Product product = mock(Product.class);
        when(product.getId()).thenReturn("productId123");
        existingOrder.setProducts(new ArrayList<>(List.of(product)));  // Create a mutable list
        when(orderRepository.getOrderById("orderId123")).thenReturn(existingOrder);
        when(orderRepository.existsOrderById("orderId123")).thenReturn(true);

        // Act
        orderService.removeProductFromOrderById("orderId123", "productId123");

        // Assert
        assertEquals(0, existingOrder.getProducts().size());
        verify(orderRepository, times(1)).save(existingOrder);
    }


    @Test
    void addProductToOrder_OrderExists_ProductAddedAndOrderUpdated() {
        // Arrange
        Order existingOrder = new Order();
        existingOrder.setId("orderId123");
        existingOrder.setProducts(new ArrayList<>());
        when(orderRepository.getOrderById("orderId123")).thenReturn(existingOrder);
        when(orderRepository.existsOrderById("orderId123")).thenReturn(true);

        ProductRequest productRequest = mock(ProductRequest.class);

        // Act
        orderService.addProductToOrder("orderId123", productRequest);

        // Assert
        assertEquals(1, existingOrder.getProducts().size());
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void deleteOrder_OrderExists_OrderDeleted() {
        // Arrange
        Order existingOrder = new Order();
        existingOrder.setId("orderId123");
        when(orderRepository.existsOrderById("orderId123")).thenReturn(true);

        // Act
        orderService.deleteOrder("orderId123");

        // Assert
        verify(orderRepository, times(1)).deleteById("orderId123");
    }

    @Test
    void deleteOrder_OrderDoesNotExist_ThrowsOrderNotFoundException() {
        // Arrange
        when(orderRepository.existsOrderById("nonExistentOrderId")).thenReturn(false);

        // Act and Assert
        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder("nonExistentOrderId"));
    }

    @Test
    void getAllOrders_ReturnsOrderResponses() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        // Act
        List<OrderResponse> orderResponses = orderService.getAllOrders();

        // Assert
        assertEquals(2, orderResponses.size());
    }
}
