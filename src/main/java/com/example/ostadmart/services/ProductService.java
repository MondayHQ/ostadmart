package com.example.ostadmart.services;

import java.util.List;
import java.util.Optional;

import com.example.ostadmart.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.ostadmart.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

// Local Imports
import com.example.ostadmart.models.Product;
import com.example.ostadmart.repositories.AuthRepository;
import com.example.ostadmart.mappers.ProductCREATEMapper;
import com.example.ostadmart.mappers.ProductUPDATEMapper;
import com.example.ostadmart.mappers.ProductResponseMapper;
import com.example.ostadmart.repositories.ProductRepository;
import com.example.ostadmart.exceptions.UserNotFoundException;
import com.example.ostadmart.exceptions.ProductNotFoundException;

@Service
public class ProductService {

    private final AuthRepository authRepository;
    private final ProductRepository productRepository;
    private final ProductUPDATEMapper productUPDATEMapper;
    private final ProductCREATEMapper productCREATEMapper;
    private final ProductResponseMapper productResponseMapper;

    public ProductService(
            AuthRepository authRepository,
            ProductRepository productRepository,
            ProductUPDATEMapper productUPDATEMapper,
            ProductCREATEMapper productCREATEMapper,
            ProductResponseMapper productResponseMapper
    ) {
        this.authRepository = authRepository;
        this.productRepository = productRepository;
        this.productUPDATEMapper = productUPDATEMapper;
        this.productCREATEMapper = productCREATEMapper;
        this.productResponseMapper = productResponseMapper;
    }

    public ProductResponseAdmin createProduct(ProductCREATERequest productCREATERequest) throws UserNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<User> userEntity = authRepository.findByEmail(userDetails.getUsername());

        if (userEntity.isEmpty()) throw new UserNotFoundException("User not found");

        Product product = productCREATEMapper.mapToEntity(productCREATERequest);
        product.setCreatedBy(userEntity.get());
        product.setUpdatedBy(userEntity.get());

        Product savedProduct = productRepository.save(product);

        return productCREATEMapper.mapToResponseDTO(savedProduct);

    }

    public List<? extends ProductResponse> getAllProducts() {

        List<Product> productEntities = productRepository.findAll();

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

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return productResponseMapper.mapToResponseDTOAdmin(product);
        }

        return productResponseMapper.mapToResponseDTO(product);

    }

    @Transactional
    public ProductUPDATEResponseDTO updateProductById(Long id, ProductUPDATERequestDTO productUPDATERequestDTO) throws ProductNotFoundException {

        Product savedProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        productUPDATEMapper.mapToExistingEntity(savedProduct, productUPDATERequestDTO);
        savedProduct.setUpdatedBy(user);

        return productUPDATEMapper.mapToResponseDTO(savedProduct);

    }

    @Transactional
    public void deleteProductById(Long id) throws ProductNotFoundException {
        Product savedProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(savedProduct);
    }

}
