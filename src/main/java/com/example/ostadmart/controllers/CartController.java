package com.example.ostadmart.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Local Imports
import com.example.ostadmart.services.CartService;
import com.example.ostadmart.dto.CartItemResponse;
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
    public ResponseEntity<List<CartItemResponse>> getAllCartItems() {
        List<CartItemResponse> cartItemResponses = cartService.getAllCartItems();

        return new ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }

    @PostMapping(path = "/items")
    public ResponseEntity<CartItemResponse> addProductToCart(
            @Valid @RequestBody AddToCartRequestDTO addToCartRequestDTO
    ) throws InsufficientStockException, ProductNotFoundException {
        CartItemResponse cartItemResponse = cartService.addProductToCart(addToCartRequestDTO);

        return ResponseEntity.ok(cartItemResponse);
    }

    @PutMapping(path = "/items/{id}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCartItemRequestDTO updateCartItemRequestDTO
    ) throws ProductNotFoundException, InsufficientStockException {
        CartItemResponse cartItemResponse = cartService.updateCartItem(id, updateCartItemRequestDTO);
        return ResponseEntity.ok(cartItemResponse);
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
