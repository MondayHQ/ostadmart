package com.example.ostadmart.dto;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

// Local Imports
import com.example.ostadmart.enums.OrderStatus;
import com.example.ostadmart.models.Payment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("order_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderTime;

    @JsonProperty("order_items")
    private List<OrderItemResponse> orderItems;

    @JsonProperty("payment")
    private Payment payment;

}
