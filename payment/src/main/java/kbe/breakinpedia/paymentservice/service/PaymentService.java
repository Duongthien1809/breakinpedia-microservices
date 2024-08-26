package kbe.breakinpedia.paymentservice.service;

import kbe.breakinpedia.paymentservice.dto.PaymentRequest;
import kbe.breakinpedia.paymentservice.dto.PaymentResponse;
import kbe.breakinpedia.paymentservice.model.Payment;
import kbe.breakinpedia.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Autowired
    private final PaymentRepository paymentRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PaymentResponse doPayment(PaymentRequest paymentRequest) {
        Payment payment = createPayment(paymentRequest);
        if (paymentProcessing().equals("success")) {
            payment.setPaymentStatus("success");
            // Set replyTo property to null or specify the appropriate response queue
            rabbitTemplate.convertAndSend("payment-exchange", "PaymentRoutingKey", payment, message -> {
                message.getMessageProperties().setReplyTo("payment-response-queue");
                return message;
            });
        }
        return mapToPaymentResponse(paymentRepository.save(payment));
    }

    //connect with extern api for payment
    String paymentProcessing() {
        //add payment methode hier after all like paypal, bank etc...
        //return new Random().nextBoolean() ? "success" : "false";
        //make process always return success
        return "success";
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(this::mapToPaymentResponse).toList();
    }

    private Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setTransactionID(UUID.randomUUID().toString());
        payment.setPaymentStatus(paymentProcessing());
        payment.setOrderID(paymentRequest.getOrderId());
        payment.setTotalCost(paymentRequest.getTotalCost());
        payment.setCurrency(paymentRequest.getCurrency());
        return payment;
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentID(payment.getId())
                .paymentStatus(payment.getPaymentStatus())
                .transactionID(payment.getTransactionID())
                .orderID(payment.getOrderID())
                .totalCost(payment.getTotalCost())
                .currency(payment.getCurrency())
                .build();
    }


}
