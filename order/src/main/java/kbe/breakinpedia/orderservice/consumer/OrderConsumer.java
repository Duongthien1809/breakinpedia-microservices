package kbe.breakinpedia.orderservice.consumer;

import kbe.breakinpedia.orderservice.dto.Payment.Payment;
import kbe.breakinpedia.orderservice.dto.product.ProductListRequest;
import kbe.breakinpedia.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Listener for the PaymentRequest-Service response
     */
    @RabbitListener(queues = "payment-response-queue")
    public void handlePaymentResponse(Payment payment) {
        log.info("Received PaymentRequest Response: {}", payment);

        // Check payment status
        if ("success".equals(payment.getPaymentStatus())) {
            orderRepository.findById(payment.getOrderID()).ifPresent(order -> {
                order.setOrderStatus("Success");
                orderRepository.save(order);
            });

            // Send message to warehouse to update warehouse
            ProductListRequest productListRequest = new ProductListRequest(orderRepository.getOrderById(payment.getOrderID()).getProducts());
            rabbitTemplate.convertAndSend("warehouse-exchange", "WarehouseRoutingKey", productListRequest);
        }
    }
}
