package kbe.breakinpedia.paymentservice.repository;

import kbe.breakinpedia.paymentservice.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
}
