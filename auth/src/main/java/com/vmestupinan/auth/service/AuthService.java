package com.vmestupinan.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vmestupinan.auth.dto.AuthRequest;
import com.vmestupinan.auth.dto.AuthResponse;
import com.vmestupinan.auth.dto.RegisterRequest;
import com.vmestupinan.auth.exception.EmailAlreadyExistsException;
import com.vmestupinan.auth.model.Role;
import com.vmestupinan.auth.model.User;
import com.vmestupinan.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Attempting to register user with email: {}", registerRequest.getEmail());

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            log.warn("Registration failed - email already in use: {}", registerRequest.getEmail());
            throw new EmailAlreadyExistsException("Email is already in use");
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        log.info("User registered successfully with email: {}", registerRequest.getEmail());

        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    public AuthResponse login(AuthRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
        } catch (AuthenticationException ex) {
            log.warn("Login failed for email: {}", request.getEmail());
            throw new BadCredentialsException("Login failed for email: " + request.getEmail());
        }

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        String jwt = jwtService.generateToken(user);
        log.info("Login successful for email: {}", request.getEmail());

        return new AuthResponse(jwt);
    }
}
