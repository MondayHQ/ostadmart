package com.example.ostadmart.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

// Local Imports
import com.example.ostadmart.enums.Category;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseAdmin implements ProductResponse {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "category")
    private Category category;

    @JsonProperty(value = "price")
    private double price;

    @JsonProperty(value = "quantity_left")
    private Integer quantityLeft;

    @JsonProperty(value = "product_photo")
    private String productPhoto;

    @JsonProperty(value = "created_by")
    private UserResponse createdBy;

    @JsonProperty(value = "updated_by")
    private UserResponse updatedBy;

}
