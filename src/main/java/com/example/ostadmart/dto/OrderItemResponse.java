package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("product")
    private ProductResponseNormal product;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("quantity")
    private Integer quantity;

}
