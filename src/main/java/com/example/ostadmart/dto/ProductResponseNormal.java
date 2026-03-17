package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

// Local Imports
import com.example.ostadmart.enums.Category;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseNormal implements ProductResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("price")
    private double price;

    @JsonProperty("quantity_left")
    private Integer quantityLeft;

    @JsonProperty("product_photo")
    private String productPhoto;

}
