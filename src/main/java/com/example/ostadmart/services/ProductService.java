package com.example.ostadmart.services;

import com.example.ostadmart.mappers.Mapper;
import org.springframework.stereotype.Service;
import com.example.ostadmart.models.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = (UserEntity) userDetails;

        ProductEntity productEntity = productRequestMapper.mapToEntity(productRequestDTO);
        productEntity.setCreated_by(userEntity);
        productEntity.setUpdated_by(userEntity);

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return productResponseMapper.mapToDTO(savedProductEntity);

    }

}
