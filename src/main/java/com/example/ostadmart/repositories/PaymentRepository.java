package com.example.ostadmart.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Local Imports
import com.example.ostadmart.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

}
