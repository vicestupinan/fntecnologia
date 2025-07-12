package com.vmestupinan.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vmestupinan.auth.dto.AuthRequest;
import com.vmestupinan.auth.dto.AuthResponse;
import com.vmestupinan.auth.dto.RegisterRequest;
import com.vmestupinan.auth.model.User;
import com.vmestupinan.auth.repository.UserRepository;
import com.vmestupinan.auth.service.JwtService;
import com.vmestupinan.auth.util.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest registerRequest) {

        User user = User.builder()
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        return new AuthResponse(jwt);
    }

    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);

        return new AuthResponse(jwt);
    }
}
