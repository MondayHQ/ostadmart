package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.Product;
import com.example.ostadmart.dto.ProductCREATERequest;
import com.example.ostadmart.dto.ProductResponseAdmin;

@Component
public class ProductCREATEMapper {

    private final ModelMapper modelMapper;

    public ProductCREATEMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------- Request Mapping ----------
    public Product mapToEntity(ProductCREATERequest productCREATERequest) {
        return modelMapper.map(productCREATERequest, Product.class);
    }

    // ---------- Response Mapping ----------
    public ProductResponseAdmin mapToResponseDTO(Product product) {
        return modelMapper.map(product, ProductResponseAdmin.class);
    }

}
