package com.example.ostadmart.config;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// Local Imports
import com.example.ostadmart.models.User;
import com.example.ostadmart.repositories.AuthRepository;

@Configuration
public class ApplicationConfig {

    private final AuthRepository authRepository;

    public ApplicationConfig(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                Optional<User> user = authRepository.findByEmail(username);

                if (user.isEmpty()) {
                    throw new UsernameNotFoundException("User not found");
                }

                return user.get();

            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();

        // This is needed for proper conversion of LocalDateTime to string in Jackson
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;

    }

}
