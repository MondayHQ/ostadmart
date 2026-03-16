package com.example.ostadmart.dto;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

// Local Imports
import com.example.ostadmart.enums.OrderStatus;
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.models.PaymentEntity;
import com.example.ostadmart.models.OrderItem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_entity")
    private UserEntity userEntity;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("order_time")
    private LocalDateTime orderTime;

    @JsonProperty("order_items")
    private List<OrderItem> orderItemEntities;

    @JsonProperty("payment")
    private PaymentEntity paymentEntity;

}
