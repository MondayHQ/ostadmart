package com.example.ostadmart.exceptions;

public record CustomErrorResponse(
        Integer status,
        String message
) {}
