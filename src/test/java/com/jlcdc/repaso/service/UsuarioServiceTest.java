package com.jlcdc.repaso.service;

import com.jlcdc.repaso.model.Usuario;
import com.jlcdc.repaso.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    @Test
    void crear_debeLanzarErrorCuandoEmailExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("mail@test.com");

        when(repository.existsByEmail("mail@test.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(usuario));
        assertEquals("Email ya existe", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_debeCodificarPasswordYGuardar() {
        Usuario usuario = new Usuario();
        usuario.setEmail("mail@test.com");
        usuario.setPassword("plain123");

        when(repository.existsByEmail("mail@test.com")).thenReturn(false);
        when(passwordEncoder.encode("plain123")).thenReturn("hashed123");
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = service.crear(usuario);

        assertEquals("hashed123", result.getPassword());
        verify(repository).save(usuario);
    }

    @Test
    void actualizar_debeMantenerPasswordCuandoNuevaEsBlank() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setNombre("Old");
        existente.setEmail("old@test.com");
        existente.setPassword("oldHash");

        Usuario actualizado = new Usuario();
        actualizado.setNombre("New");
        actualizado.setEmail("new@test.com");
        actualizado.setPassword(" ");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = service.actualizar(1L, actualizado);

        assertEquals("New", result.getNombre());
        assertEquals("new@test.com", result.getEmail());
        assertEquals("oldHash", result.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void actualizar_debeCodificarPasswordCuandoVieneInformada() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setPassword("oldHash");

        Usuario actualizado = new Usuario();
        actualizado.setNombre("New");
        actualizado.setEmail("new@test.com");
        actualizado.setPassword("newPass123");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(passwordEncoder.encode("newPass123")).thenReturn("newHash");
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = service.actualizar(1L, actualizado);

        assertEquals("newHash", result.getPassword());
        verify(passwordEncoder).encode("newPass123");
    }
}
