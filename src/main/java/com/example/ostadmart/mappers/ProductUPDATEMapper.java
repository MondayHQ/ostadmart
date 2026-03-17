package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.Product;
import com.example.ostadmart.dto.ProductUPDATERequestDTO;
import com.example.ostadmart.dto.ProductUPDATEResponseDTO;

@Component
public class ProductUPDATEMapper {

    private final ModelMapper modelMapper;

    public ProductUPDATEMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------- Request Mapping ----------
    public Product mapToEntity(ProductUPDATERequestDTO productUPDATERequestDTO) {
        return modelMapper.map(productUPDATERequestDTO, Product.class);
    }

    // ---------- Response Mapping ----------
    public ProductUPDATEResponseDTO mapToResponseDTO(Product product) {
        return modelMapper.map(product, ProductUPDATEResponseDTO.class);
    }

    // ---------- Shallow Copy ----------
    public void mapToExistingEntity(Product product, ProductUPDATERequestDTO productUPDATERequestDTO) {
        modelMapper.map(productUPDATERequestDTO, product);
    }

}
