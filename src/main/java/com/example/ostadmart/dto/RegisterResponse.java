package com.example.ostadmart.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "token")
    private String token;

}
