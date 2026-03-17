package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.Product;
import com.example.ostadmart.dto.ProductResponseNormal;
import com.example.ostadmart.dto.ProductResponseAdmin;

@Component
public class ProductResponseMapper {

    private final ModelMapper modelMapper;

    public ProductResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product mapToEntity(ProductResponseNormal productResponseNormal) {
        return modelMapper.map(productResponseNormal, Product.class);
    }

    public ProductResponseNormal mapToResponseDTO(Product product) {
        return modelMapper.map(product, ProductResponseNormal.class);
    }

    public ProductResponseAdmin mapToResponseDTOAdmin(Product product) {
        return modelMapper.map(product, ProductResponseAdmin.class);
    }

}
