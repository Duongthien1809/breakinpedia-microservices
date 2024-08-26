package kbe.breakinpedia.paymentservice.controller;

import kbe.breakinpedia.paymentservice.dto.PaymentRequest;
import kbe.breakinpedia.paymentservice.dto.PaymentResponse;
import kbe.breakinpedia.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse doPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.doPayment(paymentRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
