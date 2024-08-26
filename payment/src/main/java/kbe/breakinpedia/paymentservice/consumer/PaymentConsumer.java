package kbe.breakinpedia.paymentservice.consumer;

import kbe.breakinpedia.paymentservice.dto.PaymentRequest;
import kbe.breakinpedia.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = "currency-response-queue")
    public void handleOrderResponse(PaymentRequest paymentRequest) {
        log.info("Received PaymentRequest from currency service: {}", paymentRequest);

        if (paymentService != null) {
            paymentService.doPayment(paymentRequest);
        } else {
            log.error("PaymentService is null");
            // Handle the error appropriately, throw an exception or log an error
        }
    }
}
