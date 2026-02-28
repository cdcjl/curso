package com.jlcdc.repaso.mapper;

import com.jlcdc.repaso.dto.request.*;
import com.jlcdc.repaso.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    @Test
    void usuarioMapper_debeMapearRequestYResponse() {
        UsuarioMapper mapper = new UsuarioMapper();
        UsuarioRequest request = new UsuarioRequest("Jose", "mail@test.com", "secret123");

        Usuario model = mapper.toModel(request);
        model.setId(1L);

        assertEquals("Jose", model.getNombre());
        assertEquals("mail@test.com", model.getEmail());
        assertEquals("secret123", model.getPassword());
        assertEquals(1L, mapper.toResponse(model).getId());
    }

    @Test
    void clienteMapper_debeMapearRequestYResponse() {
        ClienteMapper mapper = new ClienteMapper();
        ClienteRequest request = new ClienteRequest("11111111-1", "Jose", "Dir", "123");

        Cliente model = mapper.toModel(request);
        assertEquals("11111111-1", model.getRutCliente());
        assertEquals("Jose", mapper.toResponse(model).getNombreCliente());
    }

    @Test
    void sucursalMapper_debeMapearRequestYResponse() {
        SucursalMapper mapper = new SucursalMapper();
        SucursalRequest request = new SucursalRequest("Centro", "Santiago");

        Sucursal model = mapper.toModel(request);
        model.setIdSucursal(10L);

        assertEquals("Centro", model.getNombreSucursal());
        assertEquals(10L, mapper.toResponse(model).getIdSucursal());
    }

    @Test
    void pedidoMapper_debeMapearRequestYResponse() {
        PedidoMapper mapper = new PedidoMapper();
        PedidoRequest request = new PedidoRequest("11111111-1", 5L, LocalDate.of(2026, 2, 28));

        Pedido model = mapper.toModel(request);
        model.setIdPedido(20L);

        assertEquals("11111111-1", model.getCliente().getRutCliente());
        assertEquals(5L, model.getSucursal().getIdSucursal());
        assertEquals(20L, mapper.toResponse(model).getIdPedido());
    }

    @Test
    void articuloPedidoMapper_debeMapearRequestYResponse() {
        ArticuloPedidoMapper mapper = new ArticuloPedidoMapper();
        ArticuloPedidoRequest request = new ArticuloPedidoRequest(20L, 30L, 2, 100.0);

        ArticuloPedido model = mapper.toModel(request);

        assertEquals(20L, model.getId().getIdPedido());
        assertEquals(30L, model.getId().getIdArticulo());
        assertEquals(200.0, mapper.toResponse(model).getMontoTotal());
    }

    @Test
    void articuloDespachoMapper_debeMapearRequestYResponse() {
        ArticuloDespachoMapper mapper = new ArticuloDespachoMapper();
        ArticuloDespachoRequest request = new ArticuloDespachoRequest(20L, 30L, "ART-30", 2, 150.0, LocalDate.of(2026, 2, 28));

        ArticuloDespacho model = mapper.toModel(request);
        model.setIdDespacho(99L);

        assertEquals(20L, model.getPedido().getIdPedido());
        assertEquals("ART-30", model.getCodArticulo());
        assertEquals(99L, mapper.toResponse(model).getIdDespacho());
    }
}
