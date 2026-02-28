package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.response.ArticuloPedidoResponse;
import com.jlcdc.repaso.exception.GlobalExceptionHandler;
import com.jlcdc.repaso.mapper.ArticuloPedidoMapper;
import com.jlcdc.repaso.model.ArticuloPedido;
import com.jlcdc.repaso.model.ArticuloPedidoId;
import com.jlcdc.repaso.security.JwtAuthenticationFilter;
import com.jlcdc.repaso.security.JwtService;
import com.jlcdc.repaso.service.ArticuloPedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticuloPedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ArticuloPedidoControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticuloPedidoService service;

    @MockitoBean
    private ArticuloPedidoMapper mapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void listar_debeRetornarArticulosPedido() throws Exception {
        ArticuloPedido model = new ArticuloPedido(new ArticuloPedidoId(1L, 2L), null, 3, 100.0);
        ArticuloPedidoResponse response = new ArticuloPedidoResponse(1L, 2L, 3, 100.0, 300.0);

        when(service.listar()).thenReturn(List.of(model));
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/articulos-pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado de articulos de pedido"))
                .andExpect(jsonPath("$.data[0].idPedido").value(1));
    }

    @Test
    void crear_debeRetornar400CuandoRequestInvalido() throws Exception {
        mockMvc.perform(post("/api/articulos-pedidos")
                        .contentType(APPLICATION_JSON)
                        .content("{\"idPedido\":0,\"idArticulo\":0,\"cantidadPedida\":0,\"precioUnitario\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validacion fallida"))
                .andExpect(jsonPath("$.errors.idPedido").exists());
    }

    @Test
    void eliminar_debeRetornarOk() throws Exception {
        doNothing().when(service).eliminar(new ArticuloPedidoId(1L, 2L));

        mockMvc.perform(delete("/api/articulos-pedidos?idPedido=1&idArticulo=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Articulo de pedido eliminado"));
    }
}
