package com.jlcdc.repaso.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import io.jsonwebtoken.ExpiredJwtException;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final String SECRET = "Q29kZXhKc29uV2ViVG9rZW5TZWNyZXRLZXlGb3JSZXBhc28xMjM0NTY3ODkw";

    @Test
    void generateToken_extractUsername_e_isTokenValid_debenFuncionar() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000L);

        UserDetails user = User.withUsername("mail@test.com").password("hash").authorities("ROLE_USER").build();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertEquals("mail@test.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void isTokenValid_debeRetornarFalseCuandoUsuarioNoCoincide() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000L);

        UserDetails tokenOwner = User.withUsername("owner@test.com").password("hash").authorities("ROLE_USER").build();
        UserDetails otherUser = User.withUsername("other@test.com").password("hash").authorities("ROLE_USER").build();

        String token = jwtService.generateToken(tokenOwner);

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void isTokenValid_debeLanzarExcepcionCuandoTokenEstaExpirado() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", -1000L);

        UserDetails user = User.withUsername("mail@test.com").password("hash").authorities("ROLE_USER").build();
        String expiredToken = jwtService.generateToken(user);

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(expiredToken, user));
    }
}
