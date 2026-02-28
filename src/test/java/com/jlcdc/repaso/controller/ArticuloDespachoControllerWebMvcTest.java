package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.response.ArticuloDespachoResponse;
import com.jlcdc.repaso.exception.GlobalExceptionHandler;
import com.jlcdc.repaso.mapper.ArticuloDespachoMapper;
import com.jlcdc.repaso.model.ArticuloDespacho;
import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.security.JwtAuthenticationFilter;
import com.jlcdc.repaso.security.JwtService;
import com.jlcdc.repaso.service.ArticuloDespachoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticuloDespachoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ArticuloDespachoControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticuloDespachoService service;

    @MockitoBean
    private ArticuloDespachoMapper mapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void obtenerPorId_debeRetornarArticuloDespacho() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(8L);

        ArticuloDespacho model = new ArticuloDespacho(1L, pedido, 9L, "COD-9", 3, 500.0, LocalDate.of(2026, 2, 3));
        ArticuloDespachoResponse response = new ArticuloDespachoResponse(1L, 8L, 9L, "COD-9", 3, 500.0, LocalDate.of(2026, 2, 3));

        when(service.obtenerPorId(1L)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/articulos-despachos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Articulo de despacho obtenido"))
                .andExpect(jsonPath("$.data.idDespacho").value(1));
    }

    @Test
    void crear_debeRetornar400CuandoRequestInvalido() throws Exception {
        mockMvc.perform(post("/api/articulos-despachos")
                        .contentType(APPLICATION_JSON)
                        .content("{\"idPedido\":0,\"idArticulo\":0,\"codArticulo\":\"\",\"cantidadDespachada\":0,\"montoDespachado\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validacion fallida"))
                .andExpect(jsonPath("$.errors.idPedido").exists());
    }
}
