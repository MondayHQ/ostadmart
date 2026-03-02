package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductUPDATERequestDTO;
import com.example.ostadmart.dto.ProductUPDATEResponseDTO;

@Component
public class ProductUPDATEMapper {

    private final ModelMapper modelMapper;

    public ProductUPDATEMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------- Request Mapping ----------
    public ProductEntity mapToEntity(ProductUPDATERequestDTO productUPDATERequestDTO) {
        return modelMapper.map(productUPDATERequestDTO, ProductEntity.class);
    }

    // ---------- Response Mapping ----------
    public ProductUPDATEResponseDTO mapToResponseDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductUPDATEResponseDTO.class);
    }

    // ---------- Shallow Copy ----------
    public void mapToExistingEntity(ProductEntity productEntity, ProductUPDATERequestDTO productUPDATERequestDTO) {
        modelMapper.map(productUPDATERequestDTO, productEntity);
    }

}
