package com.jlcdc.repaso.service;

import com.jlcdc.repaso.model.ArticuloDespacho;
import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.repository.ArticuloDespachoRepository;
import com.jlcdc.repaso.repository.PedidoRepository;
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
class ArticuloDespachoServiceTest {

    @Mock
    private ArticuloDespachoRepository repository;
    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private ArticuloDespachoService service;

    @Test
    void crear_debeLanzarErrorCuandoPedidoNoExiste() {
        ArticuloDespacho despacho = build(1L, 10L, 2, 100.0, LocalDate.now());
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(despacho));
        assertEquals("Pedido no encontrado", ex.getMessage());
    }

    @Test
    void crear_debeLanzarErrorCuandoCantidadNoEsValida() {
        ArticuloDespacho despacho = build(1L, 10L, 0, 100.0, LocalDate.now());
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(despacho));
        assertEquals("La cantidad despachada debe ser mayor a 0", ex.getMessage());
    }

    @Test
    void crear_debeLanzarErrorCuandoMontoEsNegativo() {
        ArticuloDespacho despacho = build(1L, 10L, 1, -1.0, LocalDate.now());
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new Pedido()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(despacho));
        assertEquals("El monto despachado no puede ser negativo", ex.getMessage());
    }

    @Test
    void actualizar_debeModificarSoloCamposValidos() {
        ArticuloDespacho existente = build(1L, 10L, 3, 100.0, LocalDate.of(2026, 1, 1));
        existente.setIdDespacho(5L);
        ArticuloDespacho cambios = build(1L, 10L, -1, -2.0, null);

        when(repository.findById(5L)).thenReturn(Optional.of(existente));
        when(repository.save(any(ArticuloDespacho.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArticuloDespacho result = service.actualizar(5L, cambios);

        assertEquals(3, result.getCantidadDespachada());
        assertEquals(100.0, result.getMontoDespachado());
        assertEquals(LocalDate.of(2026, 1, 1), result.getFechaDespacho());
    }

    private ArticuloDespacho build(Long idPedido, Long idArticulo, Integer cantidad, Double monto, LocalDate fecha) {
        ArticuloDespacho despacho = new ArticuloDespacho();
        Pedido pedido = new Pedido();
        pedido.setIdPedido(idPedido);
        despacho.setPedido(pedido);
        despacho.setIdArticulo(idArticulo);
        despacho.setCantidadDespachada(cantidad);
        despacho.setMontoDespachado(monto);
        despacho.setFechaDespacho(fecha);
        despacho.setCodArticulo("ART-" + idArticulo);
        return despacho;
    }
}
