package com.example.ostadmart.services;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// Local Imports
import com.example.ostadmart.models.Role;
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.dto.AuthRequestDTO;
import com.example.ostadmart.dto.AuthResponseDTO;
import com.example.ostadmart.security.jwt.JWTUtils;
import com.example.ostadmart.dto.RegisterRequestDTO;
import com.example.ostadmart.dto.RegisterResponseDTO;
import com.example.ostadmart.repositories.AuthRepository;

@Service
public class AuthService {

    private final JWTUtils jwtUtils;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OstadMartUserDetailsService userDetailsService;
    private final Mapper<UserEntity, RegisterRequestDTO> registerRequestMapper;
    private final Mapper<UserEntity, RegisterResponseDTO> registerResponseMapper;

    public AuthService(
            JWTUtils jwtUtils,
            AuthRepository authRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            OstadMartUserDetailsService userDetailsService,
            Mapper<UserEntity, RegisterRequestDTO> registerRequestMapper,
            Mapper<UserEntity, RegisterResponseDTO> registerResponseMapper
    ) {
        this.jwtUtils = jwtUtils;
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.registerRequestMapper = registerRequestMapper;
        this.registerResponseMapper = registerResponseMapper;
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {

        UserEntity userEntity = registerRequestMapper.mapToEntity(registerRequestDTO);

        userEntity.setRole(Role.USER);
        userEntity.setProfilePhoto("https://i.pravatar.cc/150?img=4");
        userEntity.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        UserEntity savedUserEntity = authRepository.save(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUserEntity.getEmail());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        RegisterResponseDTO response = registerResponseMapper.mapToDTO(savedUserEntity);
        response.setToken(jwtUtils.generateToken(authToken));

        return response;

    }

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                ));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = (UserEntity) userDetails;

        String authToken = jwtUtils.generateToken(authentication);

        return AuthResponseDTO
                .builder()
                .email(userEntity.getEmail())
                .role(userEntity.getRole().toString())
                .token(authToken)
                .build();
    }

}
