package com.vmestupinan.auth.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vmestupinan.auth.dto.AuthRequest;
import com.vmestupinan.auth.dto.AuthResponse;
import com.vmestupinan.auth.dto.RegisterRequest;
import com.vmestupinan.auth.exception.EmailAlreadyExistsException;
import com.vmestupinan.auth.model.Role;
import com.vmestupinan.auth.model.User;
import com.vmestupinan.auth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldRegisterUserSuccessfully() {
        RegisterRequest request = new RegisterRequest("Test User", "password", "test@example.com");
        User user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertEquals("jwt-token", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_shouldThrowIfEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Test User", "password", "test@example.com");
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User()));

        EmailAlreadyExistsException ex = assertThrows(EmailAlreadyExistsException.class,
                () -> authService.register(request));
        assertEquals("Email is already in use", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldAuthenticateAndReturnJwt() {
        AuthRequest request = new AuthRequest("test@example.com", "password");
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void login_shouldThrowOnBadCredentials() {
        AuthRequest request = new AuthRequest("test@example.com", "wrongpassword");

        doThrow(BadCredentialsException.class)
                .when(authenticationManager)
                .authenticate(any());

        BadCredentialsException ex = assertThrows(BadCredentialsException.class,
                () -> authService.login(request));
        assertEquals("Login failed for email: test@example.com", ex.getMessage());
    }
}
