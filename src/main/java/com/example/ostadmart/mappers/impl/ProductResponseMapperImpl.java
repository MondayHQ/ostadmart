package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import com.example.ostadmart.mappers.Mapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductResponseDTO;

@Component
public class ProductResponseMapperImpl implements Mapper<ProductEntity, ProductResponseDTO> {

    private final ModelMapper modelMapper;

    public ProductResponseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductEntity mapToEntity(ProductResponseDTO productResponseDTO) {
        return modelMapper.map(productResponseDTO, ProductEntity.class);
    }

    @Override
    public ProductResponseDTO mapToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductResponseDTO.class);
    }

}
