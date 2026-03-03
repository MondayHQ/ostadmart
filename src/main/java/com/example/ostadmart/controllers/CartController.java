package com.example.ostadmart.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Local Imports
import com.example.ostadmart.services.CartService;
import com.example.ostadmart.dto.CartItemResponseDTO;
import com.example.ostadmart.dto.AddToCartRequestDTO;
import com.example.ostadmart.dto.UpdateCartItemRequestDTO;
import com.example.ostadmart.exceptions.ProductNotFoundException;
import com.example.ostadmart.exceptions.InsufficientStockException;

@RestController
@RequestMapping(path = "/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> addProductToCart(
            @Valid @RequestBody AddToCartRequestDTO addToCartRequestDTO
    ) throws InsufficientStockException, ProductNotFoundException {
        CartItemResponseDTO cartItemResponseDTO = cartService.addProductToCart(addToCartRequestDTO);

        return ResponseEntity.ok(cartItemResponseDTO);
    }

    @PutMapping
    public ResponseEntity<CartItemResponseDTO> updateCartItem(
            @Valid @RequestBody UpdateCartItemRequestDTO updateCartItemRequestDTO
    ) throws ProductNotFoundException, InsufficientStockException {
        CartItemResponseDTO cartItemResponseDTO = cartService.updateCartItem(updateCartItemRequestDTO);
        return ResponseEntity.ok(cartItemResponseDTO);
    }

}
