package com.jlcdc.repaso.service;

import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.repository.ClienteRepository;
import com.jlcdc.repaso.repository.PedidoRepository;
import com.jlcdc.repaso.repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private PedidoService service;

    @Test
    void crear_debeLanzarErrorCuandoClienteNoExiste() {
        Pedido pedido = buildPedido("111", 1L, LocalDate.now());
        when(clienteRepository.findById("111")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(pedido));
        assertEquals("Cliente no encontrado", ex.getMessage());
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void crear_debeLanzarErrorCuandoSucursalNoExiste() {
        Pedido pedido = buildPedido("111", 1L, LocalDate.now());
        when(clienteRepository.findById("111")).thenReturn(Optional.of(new Cliente()));
        when(sucursalRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(pedido));
        assertEquals("Sucursal no encontrada", ex.getMessage());
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void crear_debeGuardarCuandoClienteYSucursalExisten() {
        Pedido pedido = buildPedido("111", 1L, LocalDate.now());
        when(clienteRepository.findById("111")).thenReturn(Optional.of(new Cliente()));
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(new Sucursal()));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido result = service.crear(pedido);

        assertNotNull(result);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void actualizar_debeActualizarFechaPedido() {
        Pedido existente = buildPedido("111", 1L, LocalDate.of(2026, 1, 1));
        existente.setIdPedido(10L);
        Pedido nuevo = buildPedido("111", 1L, LocalDate.of(2026, 2, 1));

        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido result = service.actualizar(10L, nuevo);
        assertEquals(LocalDate.of(2026, 2, 1), result.getFechaPedido());
    }

    private Pedido buildPedido(String rut, Long idSucursal, LocalDate fecha) {
        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        cliente.setRutCliente(rut);
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(idSucursal);
        pedido.setCliente(cliente);
        pedido.setSucursal(sucursal);
        pedido.setFechaPedido(fecha);
        return pedido;
    }
}
