package com.example.ostadmart.models;

import java.time.LocalDateTime;

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
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    private PaymentStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 16)
    private TransactionType transactionType;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

}
