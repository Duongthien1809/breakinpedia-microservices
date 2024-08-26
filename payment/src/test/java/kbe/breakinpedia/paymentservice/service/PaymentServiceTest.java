package kbe.breakinpedia.paymentservice.service;

import kbe.breakinpedia.paymentservice.dto.PaymentResponse;
import kbe.breakinpedia.paymentservice.model.Payment;
import kbe.breakinpedia.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    RabbitTemplate rabbitTemplate;
    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentService paymentService;

//    @Test
//    void doPayment_Success() {
//        // Arrange
//        PaymentRequest paymentRequest = new PaymentRequest();
//        // Set paymentRequest properties as needed
//
//        Payment mockedPayment = new Payment();
//        // Set mockedPayment properties as needed
//
//        // Act
//        PaymentResponse result = paymentService.doPayment(paymentRequest);
//
//        // Assert
//        assertEquals("success", result.getPaymentStatus());
//        // Add more assertions as needed
//
//        // Verify that rabbitTemplate.convertAndSend was called with the expected arguments
//        verify(rabbitTemplate).convertAndSend(
//                eq("payment-exchange"),
//                eq("PaymentRoutingKey"),
//                eq(mockedPayment),
//                any(MessagePostProcessor.class)
//        );
//    }

    @Test
    void getAllPaymentsTest() {
        Payment payment = new Payment();
        payment.setId("12345");
        payment.setTransactionID("54321");
        payment.setPaymentStatus("success");
        payment.setOrderID("123");
        payment.setTotalCost(100.0);
        payment.setCurrency("USD");

        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(payment));

        List<PaymentResponse> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getPaymentID());
    }

}