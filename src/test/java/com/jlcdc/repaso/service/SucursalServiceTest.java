package com.jlcdc.repaso.service;

import com.jlcdc.repaso.dto.SucursalPedidoDTO;
import com.jlcdc.repaso.repository.SucursalRepository;
import com.jlcdc.repaso.repository.projection.SucursalPedidoProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository repository;

    @InjectMocks
    private SucursalService service;

    @Test
    void listarSucursalConPedidos_debeMapearProyeccionADto() {
        SucursalPedidoProjection projection = new SucursalPedidoProjection() {
            @Override
            public Long getIdSucursal() {
                return 10L;
            }

            @Override
            public String getCiudad() {
                return "Santiago";
            }

            @Override
            public String getNombreSucursal() {
                return "Centro";
            }

            @Override
            public Long getIdPedido() {
                return 20L;
            }

            @Override
            public String getRutCliente() {
                return "11111111-1";
            }

            @Override
            public LocalDate getFechaPedido() {
                return LocalDate.of(2026, 2, 27);
            }
        };

        when(repository.listarSucursalConPedidos()).thenReturn(List.of(projection));

        List<SucursalPedidoDTO> result = service.listarSucursalConPedidos();

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getIdSucursal());
        assertEquals("Santiago", result.get(0).getCiudad());
        assertEquals("Centro", result.get(0).getNombreSucursal());
        assertEquals(20L, result.get(0).getIdPedido());
        assertEquals("11111111-1", result.get(0).getRutCliente());
        assertEquals(LocalDate.of(2026, 2, 27), result.get(0).getFechaPedido());
    }
}
