package com.example.ostadmart.dto;

import lombok.*;

// Local Imports
import com.example.ostadmart.enums.Category;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTOAdmin implements ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Category category;
    private double price;
    private Integer qty_left;
    private String product_photo;

    private UserDTO created_by;
    private UserDTO updated_by;

}
