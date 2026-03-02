package com.example.ostadmart.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Local Imports
import com.example.ostadmart.dto.AuthRequestDTO;
import com.example.ostadmart.dto.AuthResponseDTO;
import com.example.ostadmart.services.AuthService;
import com.example.ostadmart.dto.RegisterRequestDTO;
import com.example.ostadmart.dto.RegisterResponseDTO;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO registerResponseDTO = authService.register(registerRequestDTO);

        return new ResponseEntity<>(registerResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.login(authRequestDTO);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

}
