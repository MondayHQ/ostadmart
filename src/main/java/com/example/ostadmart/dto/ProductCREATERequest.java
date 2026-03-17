package com.example.ostadmart.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import com.example.ostadmart.enums.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCREATERequest {

    @NotBlank(message = "name is required")
    @Size(max = 128, message = "name is too long")
    @JsonProperty(value = "name")
    private String name;

    @NotBlank(message = "description is required")
    @Size(max = 512, message = "description is too long")
    @JsonProperty(value = "description")
    private String description;

    @NotNull(message = "category is required")
    @JsonProperty(value = "category")
    private Category category;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must not be negative")
    @JsonProperty(value = "price")
    private double price;

    @NotNull(message = "quantity_left is required")
    @Min(value = 0, message = "quantity_left must not be negative")
    @JsonProperty(value = "quantity_left")
    private Integer quantityLeft;

    @NotBlank(message = "product_photo is required")
    @Size(max = 255)
    @JsonProperty(value = "product_photo")
    private String productPhoto;

}
