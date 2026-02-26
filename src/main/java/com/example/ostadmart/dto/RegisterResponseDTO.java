package com.example.ostadmart.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {

    private String email;
    private String role;
    private String token;

}
