package com.example.ostadmart.services;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// Local Imports
import com.example.ostadmart.models.Role;
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.security.jwt.JWTUtils;
import com.example.ostadmart.dto.RegisterRequestDTO;
import com.example.ostadmart.dto.RegisterResponseDTO;
import com.example.ostadmart.repositories.AuthRepository;

@Service
public class AuthService {

    private final JWTUtils jwtUtils;
    private final AuthRepository authRepository;
    private final OstadMartUserDetailsService userDetailsService;
    private final Mapper<UserEntity, RegisterRequestDTO> registerRequestMapper;
    private final Mapper<UserEntity, RegisterResponseDTO> registerResponseMapper;

    public AuthService(
            AuthRepository authRepository,
            Mapper<UserEntity, RegisterRequestDTO> registerRequestMapper,
            Mapper<UserEntity, RegisterResponseDTO> registerResponseMapper,
            JWTUtils jwtUtils,
            OstadMartUserDetailsService userDetailsService
    ) {
        this.authRepository = authRepository;
        this.registerRequestMapper = registerRequestMapper;
        this.registerResponseMapper = registerResponseMapper;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {

        UserEntity userEntity = registerRequestMapper.mapToEntity(registerRequestDTO);

        userEntity.setRole(Role.ADMIN);
        userEntity.setProfilePhoto("https://i.pravatar.cc/150?img=4");

        UserEntity savedUserEntity = authRepository.save(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUserEntity.getEmail());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        RegisterResponseDTO response = registerResponseMapper.mapToDTO(savedUserEntity);
        response.setToken(jwtUtils.generateToken(SecurityContextHolder.getContext().getAuthentication()));

        return response;

    }

}
