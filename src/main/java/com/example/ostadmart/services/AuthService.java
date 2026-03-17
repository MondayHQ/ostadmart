package com.example.ostadmart.services;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// Local Imports
import com.example.ostadmart.enums.Role;
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.User;
import com.example.ostadmart.dto.AuthRequest;
import com.example.ostadmart.dto.AuthResponse;
import com.example.ostadmart.security.jwt.JWTUtils;
import com.example.ostadmart.dto.RegisterRequest;
import com.example.ostadmart.dto.RegisterResponse;
import com.example.ostadmart.repositories.AuthRepository;

@Service
public class AuthService {

    private final JWTUtils jwtUtils;
    private final CartService cartService;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final Mapper<User, RegisterRequest> registerRequestMapper;
    private final Mapper<User, RegisterResponse> registerResponseMapper;

    public AuthService(
            JWTUtils jwtUtils,
            CartService cartService,
            AuthRepository authRepository,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            Mapper<User, RegisterRequest> registerRequestMapper,
            Mapper<User, RegisterResponse> registerResponseMapper
    ) {
        this.jwtUtils = jwtUtils;
        this.cartService = cartService;
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.registerRequestMapper = registerRequestMapper;
        this.registerResponseMapper = registerResponseMapper;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {

        User user = registerRequestMapper.mapToEntity(registerRequest);

        user.setRole(Role.USER);
        user.setProfilePhoto("https://i.pravatar.cc/150?img=4");
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = authRepository.save(user);

        // ---------- CREATE cart ----------
        cartService.createCart(savedUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        RegisterResponse response = registerResponseMapper.mapToDTO(savedUser);
        response.setToken(jwtUtils.generateToken(authToken));

        return response;

    }

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                ));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        String authToken = jwtUtils.generateToken(authentication);

        return AuthResponse
                .builder()
                .email(email)
                .role(role)
                .token(authToken)
                .build();
    }

}
