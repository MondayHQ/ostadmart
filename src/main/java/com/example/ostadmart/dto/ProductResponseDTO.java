package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Local Imports
import com.example.ostadmart.enums.Category;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO implements ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Category category;
    private double price;
    private Integer qty_left;
    private String product_photo;

}
