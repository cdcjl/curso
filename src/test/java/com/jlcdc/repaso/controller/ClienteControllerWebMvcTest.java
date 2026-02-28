package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.response.ClienteResponse;
import com.jlcdc.repaso.exception.GlobalExceptionHandler;
import com.jlcdc.repaso.mapper.ClienteMapper;
import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.security.JwtAuthenticationFilter;
import com.jlcdc.repaso.security.JwtService;
import com.jlcdc.repaso.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ClienteControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService service;

    @MockitoBean
    private ClienteMapper mapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void listar_debeRetornarClientes() throws Exception {
        Cliente model = new Cliente("11-1", "Jose", "Dir", "123", null);
        ClienteResponse response = new ClienteResponse("11-1", "Jose", "Dir", "123");

        when(service.listar()).thenReturn(List.of(model));
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado de clientes"))
                .andExpect(jsonPath("$.data[0].rutCliente").value("11-1"));
    }

    @Test
    void crear_debeRetornar400CuandoRequestInvalido() throws Exception {
        mockMvc.perform(post("/api/clientes")
                        .contentType(APPLICATION_JSON)
                        .content("{\"rutCliente\":\"\",\"nombreCliente\":\"\",\"direccionCliente\":\"\",\"telefonoCliente\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validacion fallida"))
                .andExpect(jsonPath("$.errors.rutCliente").exists());
    }
}
