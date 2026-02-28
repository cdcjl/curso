package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.SucursalRequest;
import com.jlcdc.repaso.dto.response.SucursalResponse;
import com.jlcdc.repaso.exception.GlobalExceptionHandler;
import com.jlcdc.repaso.mapper.SucursalMapper;
import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.security.JwtAuthenticationFilter;
import com.jlcdc.repaso.security.JwtService;
import com.jlcdc.repaso.service.SucursalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SucursalController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class SucursalControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService service;

    @MockitoBean
    private SucursalMapper mapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void listar_debeRetornarApiResponseConData() throws Exception {
        Sucursal model = new Sucursal(10L, "Centro", "Santiago", null);
        SucursalResponse response = new SucursalResponse(10L, "Centro", "Santiago");

        when(service.listar()).thenReturn(List.of(model));
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado de sucursales"))
                .andExpect(jsonPath("$.data[0].idSucursal").value(10));
    }

    @Test
    void crear_debeRetornar400CuandoRequestInvalido() throws Exception {
        mockMvc.perform(post("/api/sucursales")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nombreSucursal\":\"\",\"ciudad\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validacion fallida"))
                .andExpect(jsonPath("$.errors.nombreSucursal").exists());
    }

    @Test
    void crear_debeRetornarApiResponseCuandoRequestValido() throws Exception {
        Sucursal model = new Sucursal(null, "Centro", "Santiago", null);
        Sucursal saved = new Sucursal(10L, "Centro", "Santiago", null);
        SucursalResponse response = new SucursalResponse(10L, "Centro", "Santiago");

        when(mapper.toModel(any(SucursalRequest.class))).thenReturn(model);
        when(service.crear(model)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/api/sucursales")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nombreSucursal\":\"Centro\",\"ciudad\":\"Santiago\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Sucursal creada"))
                .andExpect(jsonPath("$.data.idSucursal").value(10));
    }
}
