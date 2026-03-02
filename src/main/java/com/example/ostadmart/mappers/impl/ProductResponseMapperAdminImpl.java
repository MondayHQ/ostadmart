package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductResponseDTOAdmin;

@Component
public class ProductResponseMapperAdminImpl implements Mapper<ProductEntity, ProductResponseDTOAdmin> {

    private final ModelMapper modelMapper;

    public ProductResponseMapperAdminImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductEntity mapToEntity(ProductResponseDTOAdmin productResponseDTOAdmin) {
        return modelMapper.map(productResponseDTOAdmin, ProductEntity.class);
    }

    @Override
    public ProductResponseDTOAdmin mapToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductResponseDTOAdmin.class);
    }

}
