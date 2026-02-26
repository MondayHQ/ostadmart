package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Email is required")
    @Size(max = 64, message = "Maximum length exceeded")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(max = 64, message = "Maximum length exceeded")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 64, message = "Maximum length exceeded")
    private String lastName;

    @Size(max = 10)
    private String phone;

    @Size(max = 64)
    private String address;

}
