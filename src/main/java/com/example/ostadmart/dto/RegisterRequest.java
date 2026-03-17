package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Size(max = 128, message = "Maximum length exceeded, maximum 128 characters are allowed")
    @Email(message = "Invalid Email")
    @JsonProperty(value = "email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @JsonProperty(value = "password")
    private String password;

    @Size(max = 64, message = "Maximum length exceeded")
    @JsonProperty(value = "first_name")
    private String firstName;

    @Size(max = 64, message = "Maximum length exceeded")
    @JsonProperty(value = "last_name")
    private String lastName;

    @Size(max = 16)
    @JsonProperty(value = "phone")
    private String phone;

    @Size(max = 64)
    @JsonProperty(value = "address")
    private String address;

}
