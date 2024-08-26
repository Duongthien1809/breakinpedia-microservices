package com.example.currencyservice.consumer;

import com.example.currencyservice.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.example.currencyservice.service.CurrencyService.switchAmount;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrencyConsumer {

    private final RabbitTemplate rabbitTemplate;

    // Listen to request from order
    @RabbitListener(queues = "order-response-queue")
    public void handleCurrencyResponse(PaymentRequest paymentRequest) {
        log.info("Received Order Response: {}", paymentRequest);

        // Perform currency conversion and send the converted costs back to the Payment-Service
        // Set standard currency as EUR
        double convertedAmount = switchAmount(paymentRequest.getTotalCost(), "EUR", paymentRequest.getCurrency());
        paymentRequest.setTotalCost(convertedAmount);

        rabbitTemplate.convertAndSend("currency-exchange", "CurrencyRoutingKey", paymentRequest);
    }
}
