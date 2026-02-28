package com.jlcdc.repaso.service;

import com.jlcdc.repaso.dto.auth.AuthResponse;
import com.jlcdc.repaso.dto.auth.LoginRequest;
import com.jlcdc.repaso.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_debeRetornarTokenCuandoCredencialesSonValidas() {
        LoginRequest request = new LoginRequest("mail@test.com", "pass1234");
        UserDetails userDetails = User.withUsername("mail@test.com").password("hash").build();

        when(userDetailsService.loadUserByUsername("mail@test.com")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername("mail@test.com");
    }

    @Test
    void login_debePropagarErrorCuandoCredencialesInvalidas() {
        LoginRequest request = new LoginRequest("mail@test.com", "bad-pass");
        doThrow(new BadCredentialsException("bad credentials")).when(authenticationManager).authenticate(any());

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, never()).generateToken(any());
    }
}
