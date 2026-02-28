package com.jlcdc.repaso.security;

import com.jlcdc.repaso.model.Usuario;
import com.jlcdc.repaso.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_debeRetornarUserDetailsCuandoUsuarioExiste() {
        Usuario usuario = new Usuario(1L, "Jose", "mail@test.com", "hash123");
        when(usuarioRepository.findByEmail("mail@test.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("mail@test.com");

        assertEquals("mail@test.com", userDetails.getUsername());
        assertEquals("hash123", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_debeLanzarExcepcionCuandoUsuarioNoExiste() {
        when(usuarioRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("notfound@test.com"));
    }
}
