package com.jlcdc.repaso.service;

import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void crear_debeLanzarErrorCuandoRutExiste() {
        Cliente cliente = new Cliente();
        cliente.setRutCliente("11111111-1");

        when(repository.existsById("11111111-1")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(cliente));
        assertEquals("Cliente con RUT 11111111-1 ya existe", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_debeGuardarCuandoRutNoExiste() {
        Cliente cliente = new Cliente("11111111-1", "Jose", "Dir", "123", null);
        when(repository.existsById("11111111-1")).thenReturn(false);
        when(repository.save(cliente)).thenReturn(cliente);

        Cliente result = service.crear(cliente);

        assertEquals("11111111-1", result.getRutCliente());
        verify(repository).save(cliente);
    }

    @Test
    void obtenerPorRut_debeLanzarErrorCuandoNoExiste() {
        when(repository.findById("1-9")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.obtenerPorRut("1-9"));
    }

    @Test
    void actualizar_debeModificarCamposYGuardar() {
        Cliente existente = new Cliente("11111111-1", "Old", "OldDir", "000", null);
        Cliente actualizado = new Cliente("11111111-1", "New", "NewDir", "999", null);

        when(repository.findById("11111111-1")).thenReturn(Optional.of(existente));
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente result = service.actualizar("11111111-1", actualizado);

        assertEquals("New", result.getNombreCliente());
        assertEquals("NewDir", result.getDireccionCliente());
        assertEquals("999", result.getTelefonoCliente());
    }
}
