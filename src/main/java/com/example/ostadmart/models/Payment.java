package com.example.ostadmart.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.ostadmart.enums.PaymentInitStatus;
import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Local Imports
import com.example.ostadmart.enums.PaymentStatus;
import com.example.ostadmart.enums.TransactionType;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "transaction_id", length = 30, unique = true, nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    private PaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_provider", length = 32, nullable = false)
    private String paymentProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 16)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_init_status", length = 16)
    private PaymentInitStatus paymentInitStatus;

    @Column(name = "session_key", length = 64)
    private String sessionKey;

    @Column(name = "gateway_page_url")
    private String gatewayPageURL;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
