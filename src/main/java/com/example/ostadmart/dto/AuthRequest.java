package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotNull(message = "Email is required")
    @Email(message = "Invalid Email")
    @JsonProperty(value = "email")
    private String email;

    @NotBlank(message = "Password is required")
    @JsonProperty(value = "password")
    private String password;

}
