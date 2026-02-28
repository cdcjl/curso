package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.PedidoRequest;
import com.jlcdc.repaso.dto.response.PedidoResponse;
import com.jlcdc.repaso.exception.GlobalExceptionHandler;
import com.jlcdc.repaso.mapper.PedidoMapper;
import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.security.JwtAuthenticationFilter;
import com.jlcdc.repaso.security.JwtService;
import com.jlcdc.repaso.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class PedidoControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService service;

    @MockitoBean
    private PedidoMapper mapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void listar_debeRetornarPedidos() throws Exception {
        Cliente cliente = new Cliente("11-1", "Jose", "Dir", "123", null);
        Sucursal sucursal = new Sucursal(5L, "Centro", "Santiago", null);
        Pedido model = new Pedido(1L, cliente, sucursal, LocalDate.of(2026, 2, 1), null, null);
        PedidoResponse response = new PedidoResponse(1L, "11-1", 5L, LocalDate.of(2026, 2, 1));

        when(service.listar()).thenReturn(List.of(model));
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado de pedidos"))
                .andExpect(jsonPath("$.data[0].idPedido").value(1));
    }

    @Test
    void obtenerPorClienteRut_debeRetornarPedidos() throws Exception {
        Cliente cliente = new Cliente("11-1", "Jose", "Dir", "123", null);
        Sucursal sucursal = new Sucursal(5L, "Centro", "Santiago", null);
        Pedido model = new Pedido(2L, cliente, sucursal, LocalDate.of(2026, 2, 2), null, null);
        PedidoResponse response = new PedidoResponse(2L, "11-1", 5L, LocalDate.of(2026, 2, 2));

        when(service.obtenerPorClienteRut("11-1")).thenReturn(List.of(model));
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/pedidos/cliente/11-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedidos por cliente"))
                .andExpect(jsonPath("$.data[0].rutCliente").value("11-1"));
    }

    @Test
    void crear_debeRetornar400CuandoRequestInvalido() throws Exception {
        mockMvc.perform(post("/api/pedidos")
                        .contentType(APPLICATION_JSON)
                        .content("{\"rutCliente\":\"\",\"idSucursal\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validacion fallida"))
                .andExpect(jsonPath("$.errors.rutCliente").exists());
    }

    @Test
    void crear_debeRetornarOkCuandoRequestValido() throws Exception {
        Cliente cliente = new Cliente("11-1", "Jose", "Dir", "123", null);
        Sucursal sucursal = new Sucursal(5L, "Centro", "Santiago", null);
        Pedido model = new Pedido(null, cliente, sucursal, LocalDate.of(2026, 2, 3), null, null);
        Pedido saved = new Pedido(9L, cliente, sucursal, LocalDate.of(2026, 2, 3), null, null);
        PedidoResponse response = new PedidoResponse(9L, "11-1", 5L, LocalDate.of(2026, 2, 3));

        when(mapper.toModel(any(PedidoRequest.class))).thenReturn(model);
        when(service.crear(model)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(APPLICATION_JSON)
                        .content("{\"rutCliente\":\"11-1\",\"idSucursal\":5,\"fechaPedido\":\"2026-02-03\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pedido creado"))
                .andExpect(jsonPath("$.data.idPedido").value(9));
    }
}
