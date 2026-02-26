package com.example.ostadmart.services;

import com.example.ostadmart.mappers.Mapper;
import org.springframework.stereotype.Service;

// Local Imports
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.dto.ProductRequestDTO;
import com.example.ostadmart.dto.ProductResponseDTO;
import com.example.ostadmart.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Mapper<ProductEntity, ProductRequestDTO> productRequestMapper;
    private final Mapper<ProductEntity, ProductResponseDTO> productResponseMapper;

    public ProductService(
            ProductRepository productRepository,
            Mapper<ProductEntity, ProductRequestDTO> productRequestMapper,
            Mapper<ProductEntity, ProductResponseDTO> productResponseMapper
    ) {
        this.productRepository = productRepository;
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        ProductEntity productEntity = productRequestMapper.mapToEntity(productRequestDTO);

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return productResponseMapper.mapToDTO(savedProductEntity);

    }

}
