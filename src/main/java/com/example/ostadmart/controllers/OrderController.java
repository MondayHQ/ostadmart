package com.example.ostadmart.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Local Imports
import com.example.ostadmart.dto.OrderResponse;
import com.example.ostadmart.services.OrderService;
import com.example.ostadmart.dto.OrderCreateRequest;
import com.example.ostadmart.exceptions.CartEmptyException;
import com.example.ostadmart.exceptions.UserNotFoundException;
import com.example.ostadmart.exceptions.InsufficientStockException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) throws UserNotFoundException, InsufficientStockException, CartEmptyException {
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

}
