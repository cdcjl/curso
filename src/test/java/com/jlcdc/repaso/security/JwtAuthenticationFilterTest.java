package com.jlcdc.repaso.security;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_debeContinuarCuandoNoHayAuthorizationHeader() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_debeSetearAutenticacionCuandoTokenEsValido() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-ok");
        MockHttpServletResponse response = new MockHttpServletResponse();

        UserDetails user = User.withUsername("mail@test.com").password("hash").authorities("ROLE_USER").build();
        when(jwtService.extractUsername("token-ok")).thenReturn("mail@test.com");
        when(userDetailsService.loadUserByUsername("mail@test.com")).thenReturn(user);
        when(jwtService.isTokenValid("token-ok", user)).thenReturn(true);

        filter.doFilter(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("mail@test.com", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noDebeSetearAutenticacionCuandoTokenEsInvalido() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-bad");
        MockHttpServletResponse response = new MockHttpServletResponse();

        UserDetails user = User.withUsername("mail@test.com").password("hash").authorities("ROLE_USER").build();
        when(jwtService.extractUsername("token-bad")).thenReturn("mail@test.com");
        when(userDetailsService.loadUserByUsername("mail@test.com")).thenReturn(user);
        when(jwtService.isTokenValid("token-bad", user)).thenReturn(false);

        filter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noDebeRecargarUsuarioCuandoYaHayAutenticacion() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-ok");
        MockHttpServletResponse response = new MockHttpServletResponse();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("existing", null)
        );
        when(jwtService.extractUsername("token-ok")).thenReturn("mail@test.com");

        filter.doFilter(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(filterChain).doFilter(request, response);
        assertEquals("existing", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
