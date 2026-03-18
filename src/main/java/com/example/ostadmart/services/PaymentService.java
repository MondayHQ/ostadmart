package com.example.ostadmart.services;

import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

// Local Imports
import com.example.ostadmart.models.Order;
import com.example.ostadmart.models.Payment;
import com.example.ostadmart.enums.OrderStatus;
import com.example.ostadmart.dto.PaymentRequest;
import com.example.ostadmart.enums.PaymentStatus;
import com.example.ostadmart.enums.PaymentInitStatus;
import com.example.ostadmart.dto.PaymentProviderResponse;
import com.example.ostadmart.repositories.OrderRepository;
import com.example.ostadmart.repositories.PaymentRepository;
import com.example.ostadmart.exceptions.OrderNotFoundException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentProvider paymentProvider;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentProviderResponse initiatePayment(PaymentRequest paymentRequest) throws OrderNotFoundException {

        Long orderId = paymentRequest.getOrderId();
        String transactionId = "TRX_" + orderId;

        Optional<Payment> payment = paymentRepository.findByTransactionId(transactionId);

        if (payment.isPresent()) {
            if (payment.get().getPaymentInitStatus().equals(PaymentInitStatus.SUCCESS)) {
                return PaymentProviderResponse.builder()
                        .status(payment.get().getPaymentInitStatus().toString())
                        .sessionKey(payment.get().getSessionKey())
                        .gatewayPageURL(payment.get().getGatewayPageURL())
                        .build();
            }
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Payment newPayment = Payment.builder()
                .amount(order.getTotalAmount())
                .transactionId(transactionId)
                .status(PaymentStatus.PENDING)
                .paymentProvider("SSLCOMMERZ")
                .order(order)
                .build();
        paymentRepository.save(newPayment);

        PaymentProviderResponse response = paymentProvider.initiatePayment(newPayment);

        newPayment.setPaymentInitStatus(PaymentInitStatus.valueOf(response.getStatus()));
        newPayment.setSessionKey(response.getSessionKey());
        newPayment.setGatewayPageURL(response.getGatewayPageURL());

        return response;
    }

    @Transactional
    public String finalizePayment(Map<String, String> requestParams) {

        String transactionId = requestParams.get("tran_id");
        Optional<Payment> payment = paymentRepository.findByTransactionId(transactionId);

        boolean paymentValid = paymentProvider.validatePayment(requestParams);

        if (paymentValid) {
            payment.ifPresent(value -> {
                value.setStatus(PaymentStatus.COMPLETED);

                Order order = payment.get().getOrder();
                order.setStatus(OrderStatus.CONFIRMED);
            });

            return "OK";
        }

        String paymentStatus = requestParams.get("status");

        payment.ifPresent(value -> {
            if ("VALID".equals(paymentStatus)) value.setStatus(PaymentStatus.COMPLETED);
            if ("CANCELLED".equals(paymentStatus)) value.setStatus(PaymentStatus.CANCELED);

            if (!"VALID".equals(paymentStatus) && !"CANCELLED".equals(paymentStatus)) {
                value.setStatus(PaymentStatus.FAILED);
            }
        });

        return "FAILED";
    }

}
