package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTOFull {

    private Long id;
    private Double total_amount;
    private List<ProductResponseDTO> products;

}
