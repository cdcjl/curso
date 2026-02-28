package com.jlcdc.repaso.service;

import com.jlcdc.repaso.model.ArticuloPedido;
import com.jlcdc.repaso.model.ArticuloPedidoId;
import com.jlcdc.repaso.repository.ArticuloPedidoRepository;
import com.jlcdc.repaso.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticuloPedidoServiceTest {

    @Mock
    private ArticuloPedidoRepository repository;
    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private ArticuloPedidoService service;

    @Test
    void crear_debeLanzarErrorCuandoPedidoNoExiste() {
        ArticuloPedido articulo = build(1L, 2L, 1, 100.0);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(articulo));
        assertEquals("Pedido no encontrado", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_debeLanzarErrorCuandoCantidadNoEsValida() {
        ArticuloPedido articulo = build(1L, 2L, 0, 100.0);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new com.jlcdc.repaso.model.Pedido()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(articulo));
        assertEquals("La cantidad debe ser mayor a 0", ex.getMessage());
    }

    @Test
    void crear_debeLanzarErrorCuandoPrecioEsNegativo() {
        ArticuloPedido articulo = build(1L, 2L, 1, -1.0);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new com.jlcdc.repaso.model.Pedido()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crear(articulo));
        assertEquals("El precio unitario no puede ser negativo", ex.getMessage());
    }

    @Test
    void crear_debeGuardarCuandoDatosValidos() {
        ArticuloPedido articulo = build(1L, 2L, 2, 100.0);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(new com.jlcdc.repaso.model.Pedido()));
        when(repository.save(articulo)).thenReturn(articulo);

        ArticuloPedido result = service.crear(articulo);
        assertEquals(200.0, result.getMontoTotal());
        verify(repository).save(articulo);
    }

    @Test
    void actualizar_debeModificarSoloCamposValidos() {
        ArticuloPedidoId id = new ArticuloPedidoId(1L, 2L);
        ArticuloPedido existente = build(1L, 2L, 3, 100.0);
        ArticuloPedido cambios = build(1L, 2L, -1, -5.0);

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(ArticuloPedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArticuloPedido result = service.actualizar(id, cambios);

        assertEquals(3, result.getCantidadPedida());
        assertEquals(100.0, result.getPrecioUnitario());
    }

    private ArticuloPedido build(Long idPedido, Long idArticulo, Integer cantidad, Double precio) {
        ArticuloPedido articulo = new ArticuloPedido();
        articulo.setId(new ArticuloPedidoId(idPedido, idArticulo));
        articulo.setCantidadPedida(cantidad);
        articulo.setPrecioUnitario(precio);
        return articulo;
    }
}
