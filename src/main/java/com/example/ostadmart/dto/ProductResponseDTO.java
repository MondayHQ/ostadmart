package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import com.example.ostadmart.model.Category;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    @NotBlank
    private String name;
    private String description;
    private Category category;
    private double price;

    @Min(value = 0, message = "Negative value is not allowed")
    private Integer qty_left;
    private String product_photo;

}
