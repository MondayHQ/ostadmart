package com.example.ostadmart.models;

import java.util.List;
import java.util.Collection;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// Local Imports
import com.example.ostadmart.enums.Role;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64)
    @Email
    @Column(unique = true, length = 64)
    private String email;

    @NotBlank
    @Column(length = 60)
    private String password;

    @NotBlank
    @Size(max = 64)
    private String first_name;

    @NotBlank
    @Size(max = 64)
    private String last_name;

    @Column(nullable = false)
    private Role role;

    @Column(unique = true, length = 10)
    private String phone;

    @Column(length = 64)
    private String address;

    @Column(length = 512)
    private String profilePhoto;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}
