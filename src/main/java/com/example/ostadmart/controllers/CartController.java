package com.example.ostadmart.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @GetMapping(path = "/items")
    public ResponseEntity<List<CartItemResponseDTO>> getAllCartItems() {
        List<CartItemResponseDTO> cartItemResponseDTOS = cartService.getAllCartItems();

        return new ResponseEntity<>(cartItemResponseDTOS, HttpStatus.OK);
    }

    @PostMapping(path = "/items")
    public ResponseEntity<CartItemResponseDTO> addProductToCart(
            @Valid @RequestBody AddToCartRequestDTO addToCartRequestDTO
    ) throws InsufficientStockException, ProductNotFoundException {
        CartItemResponseDTO cartItemResponseDTO = cartService.addProductToCart(addToCartRequestDTO);

        return ResponseEntity.ok(cartItemResponseDTO);
    }

    @PutMapping(path = "/items/{id}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCartItemRequestDTO updateCartItemRequestDTO
    ) throws ProductNotFoundException, InsufficientStockException {
        CartItemResponseDTO cartItemResponseDTO = cartService.updateCartItem(id, updateCartItemRequestDTO);
        return ResponseEntity.ok(cartItemResponseDTO);
    }

    @DeleteMapping(path = "/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable Long id) throws ProductNotFoundException {
        cartService.removeCartItem(id);
    }

    @DeleteMapping(path = "/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart() {
        cartService.clearCart();
    }

}
