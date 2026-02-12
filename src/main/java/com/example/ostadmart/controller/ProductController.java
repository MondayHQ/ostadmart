package com.example.ostadmart.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Local Imports
import com.example.ostadmart.dto.ProductRequestDTO;
import com.example.ostadmart.dto.ProductResponseDTO;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO productResponseDTO = ProductResponseDTO
                .builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .qty_left(product.getQty_left())
                .product_photo(product.getProduct_photo())
                .build();

        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = List.of();

        return ResponseEntity.ok(products);
    }

}
