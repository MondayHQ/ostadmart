package com.example.ostadmart.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Local Imports
import com.example.ostadmart.dto.ProductRequestDTO;
import com.example.ostadmart.dto.ProductResponseDTO;
import com.example.ostadmart.services.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO product) {

        ProductResponseDTO productResponseDTO = productService.createProduct(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = List.of();

        return ResponseEntity.ok(products);
    }

}
