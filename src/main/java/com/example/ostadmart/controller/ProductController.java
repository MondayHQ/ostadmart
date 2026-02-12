package com.example.ostadmart.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

// Local Imports
import com.example.ostadmart.dto.ProductRequestDTO;
import com.example.ostadmart.dto.ProductResponseDTO;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @Value("${secret.configurations.update}")
    private Integer hello;

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO productResponseDTO = ProductResponseDTO
                .builder()
                .build();

        return null;
    }

    @GetMapping
    public ResponseEntity<Integer> getProducts() {
        return ResponseEntity.ok(hello);
    }

}
