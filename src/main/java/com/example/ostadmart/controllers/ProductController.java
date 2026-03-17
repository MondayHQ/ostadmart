package com.example.ostadmart.controllers;

import java.util.List;

import com.example.ostadmart.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

// Local Imports
import com.example.ostadmart.dto.*;
import com.example.ostadmart.services.ProductService;
import com.example.ostadmart.exceptions.ProductNotFoundException;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseAdmin> createProduct(@Valid @RequestBody ProductCREATERequest product) throws UserNotFoundException {

        ProductResponseAdmin productResponseAdmin = productService.createProduct(product);
        return new ResponseEntity<>(productResponseAdmin, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<? extends ProductResponse>> getAllProducts() {
        List<? extends ProductResponse> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductUPDATEResponseDTO> updateProductById(
            @PathVariable Long id,
            @Valid @RequestBody ProductUPDATERequestDTO productUPDATERequestDTO
    ) throws ProductNotFoundException {
        ProductUPDATEResponseDTO productUPDATEResponseDTO = productService
                .updateProductById(
                        id,
                        productUPDATERequestDTO
                );

        return ResponseEntity.ok(productUPDATEResponseDTO);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
    }

}
