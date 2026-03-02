package com.example.ostadmart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Local Imports
import com.example.ostadmart.models.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private Role role;
    private String phone;
    private String address;
    private String profilePhoto;

}
