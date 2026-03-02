package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductResponseDTO;
import com.example.ostadmart.dto.ProductResponseDTOAdmin;

@Component
public class ProductResponseMapper {

    private final ModelMapper modelMapper;

    public ProductResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductEntity mapToEntity(ProductResponseDTO productResponseDTO) {
        return modelMapper.map(productResponseDTO, ProductEntity.class);
    }

    public ProductResponseDTO mapToResponseDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductResponseDTO.class);
    }

    public ProductResponseDTOAdmin mapToResponseDTOAdmin(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductResponseDTOAdmin.class);
    }

}
