package com.example.ostadmart.exceptions;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidJWTTokenResponse {

    private int status;
    private String message;

}
