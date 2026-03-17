package com.example.ostadmart.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Local Imports
import com.example.ostadmart.dto.AuthRequest;
import com.example.ostadmart.dto.AuthResponse;
import com.example.ostadmart.services.AuthService;
import com.example.ostadmart.dto.RegisterRequest;
import com.example.ostadmart.dto.RegisterResponse;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = authService.register(registerRequest);

        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
