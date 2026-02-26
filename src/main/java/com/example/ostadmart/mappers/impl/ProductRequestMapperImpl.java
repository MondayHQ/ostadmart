package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductRequestDTO;

@Component
public class ProductRequestMapperImpl implements Mapper<ProductEntity, ProductRequestDTO> {

    private final ModelMapper modelMapper;

    public ProductRequestMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public ProductEntity mapToEntity(ProductRequestDTO productRequestDTO) {
        return modelMapper.map(productRequestDTO, ProductEntity.class);
    }

    @Override
    public ProductRequestDTO mapToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductRequestDTO.class);
    }

}
