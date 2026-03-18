package com.example.ostadmart.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Local Imports
import com.example.ostadmart.dto.PaymentRequest;
import com.example.ostadmart.services.PaymentService;
import com.example.ostadmart.dto.PaymentProviderResponse;
import com.example.ostadmart.exceptions.OrderNotFoundException;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentProviderResponse> createPayment(@Valid @RequestBody PaymentRequest paymentRequest) throws OrderNotFoundException {

        PaymentProviderResponse response = paymentService.initiatePayment(paymentRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping(path = "/ipn")
    public ResponseEntity<String> handleIPN(@RequestParam Map<String, String> requestParams) {

        log.info("Received IPN request: {}", requestParams);

        String result = paymentService.finalizePayment(requestParams);

        if ("OK".equals(result)) {
            return ResponseEntity.ok("OK");
        }

        return ResponseEntity.badRequest().body("Invalid IPN");

    }

}
