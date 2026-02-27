package com.jlcdc.repaso.mapper;

import com.jlcdc.repaso.dto.request.PedidoRequest;
import com.jlcdc.repaso.dto.response.PedidoResponse;
import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido toModel(PedidoRequest dto) {
        Pedido pedido = new Pedido();

        Cliente cliente = new Cliente();
        cliente.setRutCliente(dto.getRutCliente());
        pedido.setCliente(cliente);

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(dto.getIdSucursal());
        pedido.setSucursal(sucursal);

        pedido.setFechaPedido(dto.getFechaPedido());
        return pedido;
    }

    public PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getIdPedido(),
                pedido.getCliente() != null ? pedido.getCliente().getRutCliente() : null,
                pedido.getSucursal() != null ? pedido.getSucursal().getIdSucursal() : null,
                pedido.getFechaPedido()
        );
    }
}
