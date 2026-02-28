package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.UsuarioRequest;
import com.jlcdc.repaso.dto.response.UsuarioResponse;
import com.jlcdc.repaso.exception.GlobalExceptionHandler;
import com.jlcdc.repaso.mapper.UsuarioMapper;
import com.jlcdc.repaso.model.Usuario;
import com.jlcdc.repaso.security.JwtAuthenticationFilter;
import com.jlcdc.repaso.security.JwtService;
import com.jlcdc.repaso.service.UsuarioService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UsuarioControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;

    @MockitoBean
    private UsuarioMapper mapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void crear_debeRetornarApiResponse() throws Exception {
        Usuario model = new Usuario(null, "Jose", "mail@test.com", "hash");
        Usuario saved = new Usuario(1L, "Jose", "mail@test.com", "hash");
        UsuarioResponse response = new UsuarioResponse(1L, "Jose", "mail@test.com");

        when(mapper.toModel(any(UsuarioRequest.class))).thenReturn(model);
        when(service.crear(model)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nombre\":\"Jose\",\"email\":\"mail@test.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario creado"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Jose"));
    }

    @Test
    void crear_debeRetornar400CuandoRequestInvalido() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nombre\":\"\",\"email\":\"bad-email\",\"password\":\"123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validacion fallida"))
                .andExpect(jsonPath("$.errors.nombre").exists());
    }

    @Test
    void listar_debeRetornarUsuarios() throws Exception {
        Usuario model = new Usuario(1L, "Jose", "mail@test.com", "hash");
        UsuarioResponse response = new UsuarioResponse(1L, "Jose", "mail@test.com");

        when(service.listar()).thenReturn(List.of(model));
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado de usuarios"))
                .andExpect(jsonPath("$.data[0].id").value(1));
    }

    @Test
    void obtener_debeRetornarUsuarioPorId() throws Exception {
        Usuario model = new Usuario(2L, "Ana", "ana@test.com", "hash");
        UsuarioResponse response = new UsuarioResponse(2L, "Ana", "ana@test.com");

        when(service.obtenerPorId(2L)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(response);

        mockMvc.perform(get("/api/usuarios/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario obtenido"))
                .andExpect(jsonPath("$.data.id").value(2));
    }

    @Test
    void actualizar_debeRetornarUsuarioActualizado() throws Exception {
        Usuario model = new Usuario(null, "Jose Mod", "mail2@test.com", "hash2");
        Usuario updated = new Usuario(1L, "Jose Mod", "mail2@test.com", "hash2");
        UsuarioResponse response = new UsuarioResponse(1L, "Jose Mod", "mail2@test.com");

        when(mapper.toModel(any(UsuarioRequest.class))).thenReturn(model);
        when(service.actualizar(1L, model)).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(response);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"nombre\":\"Jose Mod\",\"email\":\"mail2@test.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario actualizado"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void eliminar_debeRetornarOk() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario eliminado"));
    }
}
