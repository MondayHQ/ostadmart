package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {

    private Long id;
    private CartResponseDTOMinimal cart;
    private ProductResponseDTO product;
    private Double unit_price;
    private Integer qty;

}
