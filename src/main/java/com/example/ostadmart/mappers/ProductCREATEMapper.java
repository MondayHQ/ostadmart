package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductCREATERequestDTO;
import com.example.ostadmart.dto.ProductResponseDTOAdmin;

@Component
public class ProductCREATEMapper {

    private final ModelMapper modelMapper;

    public ProductCREATEMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------- Request Mapping ----------
    public ProductEntity mapToEntity(ProductCREATERequestDTO productCREATERequestDTO) {
        return modelMapper.map(productCREATERequestDTO, ProductEntity.class);
    }

    // ---------- Response Mapping ----------
    public ProductResponseDTOAdmin mapToResponseDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductResponseDTOAdmin.class);
    }

}
