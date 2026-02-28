package com.jlcdc.repaso.exception;

import com.jlcdc.repaso.dto.api.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBadCredentials_debeRetornar401SinTrace() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/auth/login");

        ApiErrorResponse body = handler.handleBadCredentials(new BadCredentialsException("bad"), request).getBody();

        assertNotNull(body);
        assertFalse(body.isSuccess());
        assertEquals(401, body.getStatus());
        assertEquals("Unauthorized", body.getError());
        assertEquals("Credenciales invalidas", body.getMessage());
        assertNull(body.getErrors());
    }

    @Test
    void handleRuntime_debeRetornar400() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/sucursales");

        ApiErrorResponse body = handler.handleRuntime(new RuntimeException("Error negocio"), request).getBody();

        assertNotNull(body);
        assertFalse(body.isSuccess());
        assertEquals(400, body.getStatus());
        assertEquals("Bad Request", body.getError());
        assertEquals("Error negocio", body.getMessage());
    }

    @Test
    void handleAccessDenied_debeRetornar403() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/usuarios");

        ApiErrorResponse body = handler.handleAccessDenied(new AccessDeniedException("forbidden"), request).getBody();

        assertNotNull(body);
        assertFalse(body.isSuccess());
        assertEquals(403, body.getStatus());
        assertEquals("Forbidden", body.getError());
    }
}
