package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.Product;
import com.example.ostadmart.dto.ProductResponseAdmin;

@Component
public class ProductResponseMapperAdminImpl implements Mapper<Product, ProductResponseAdmin> {

    private final ModelMapper modelMapper;

    public ProductResponseMapperAdminImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Product mapToEntity(ProductResponseAdmin productResponseAdmin) {
        return modelMapper.map(productResponseAdmin, Product.class);
    }

    @Override
    public ProductResponseAdmin mapToDTO(Product product) {
        return modelMapper.map(product, ProductResponseAdmin.class);
    }

}
