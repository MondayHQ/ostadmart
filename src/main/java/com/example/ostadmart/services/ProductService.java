package com.example.ostadmart.services;

import java.util.List;

import com.example.ostadmart.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.ostadmart.models.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

// Local Imports
import com.example.ostadmart.models.ProductEntity;
import com.example.ostadmart.mappers.ProductCREATEMapper;
import com.example.ostadmart.mappers.ProductUPDATEMapper;
import com.example.ostadmart.mappers.ProductResponseMapper;
import com.example.ostadmart.repositories.ProductRepository;
import com.example.ostadmart.exceptions.ProductNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductUPDATEMapper productUPDATEMapper;
    private final ProductCREATEMapper productCREATEMapper;
    private final ProductResponseMapper productResponseMapper;

    public ProductService(
            ProductRepository productRepository,
            ProductUPDATEMapper productUPDATEMapper,
            ProductCREATEMapper productCREATEMapper,
            ProductResponseMapper productResponseMapper
    ) {
        this.productRepository = productRepository;
        this.productUPDATEMapper = productUPDATEMapper;
        this.productCREATEMapper = productCREATEMapper;
        this.productResponseMapper = productResponseMapper;
    }

    public ProductResponseDTOAdmin createProduct(ProductCREATERequestDTO productCREATERequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = (UserEntity) userDetails;

        ProductEntity productEntity = productCREATEMapper.mapToEntity(productCREATERequestDTO);
        productEntity.setCreated_by(userEntity);
        productEntity.setUpdated_by(userEntity);

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return productCREATEMapper.mapToResponseDTO(savedProductEntity);

    }

    public List<? extends ProductResponse> getAllProducts() {

        List<ProductEntity> productEntities = productRepository.findAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return productEntities
                    .stream()
                    .map(productResponseMapper::mapToResponseDTOAdmin)
                    .toList();
        }

        return productEntities
                .stream()
                .map(productResponseMapper::mapToResponseDTO)
                .toList();

    }

    public ProductResponse getProductById(Long id) throws ProductNotFoundException {

        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return productResponseMapper.mapToResponseDTOAdmin(productEntity);
        }

        return productResponseMapper.mapToResponseDTO(productEntity);

    }

    @Transactional
    public ProductUPDATEResponseDTO updateProductById(Long id, ProductUPDATERequestDTO productUPDATERequestDTO) throws ProductNotFoundException {

        ProductEntity savedProductEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        productUPDATEMapper.mapToExistingEntity(savedProductEntity, productUPDATERequestDTO);
        savedProductEntity.setUpdated_by(userEntity);

        return productUPDATEMapper.mapToResponseDTO(savedProductEntity);

    }

    @Transactional
    public void deleteProductById(Long id) throws ProductNotFoundException {
        ProductEntity savedProductEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(savedProductEntity);
    }

}
