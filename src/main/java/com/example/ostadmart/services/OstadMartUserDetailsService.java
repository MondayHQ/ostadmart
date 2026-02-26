package com.example.ostadmart.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// Local Imports
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.repositories.AuthRepository;

@Service
public class OstadMartUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public OstadMartUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = authRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();

    }

}
