package com.jlcdc.repaso.mapper;

import com.jlcdc.repaso.dto.request.ArticuloPedidoRequest;
import com.jlcdc.repaso.dto.response.ArticuloPedidoResponse;
import com.jlcdc.repaso.model.ArticuloPedido;
import com.jlcdc.repaso.model.ArticuloPedidoId;
import com.jlcdc.repaso.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class ArticuloPedidoMapper {

    public ArticuloPedido toModel(ArticuloPedidoRequest dto) {
        ArticuloPedido articuloPedido = new ArticuloPedido();
        articuloPedido.setId(new ArticuloPedidoId(dto.getIdPedido(), dto.getIdArticulo()));

        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        articuloPedido.setPedido(pedido);

        articuloPedido.setCantidadPedida(dto.getCantidadPedida());
        articuloPedido.setPrecioUnitario(dto.getPrecioUnitario());
        return articuloPedido;
    }

    public ArticuloPedidoResponse toResponse(ArticuloPedido articuloPedido) {
        return new ArticuloPedidoResponse(
                articuloPedido.getId() != null ? articuloPedido.getId().getIdPedido() : null,
                articuloPedido.getId() != null ? articuloPedido.getId().getIdArticulo() : null,
                articuloPedido.getCantidadPedida(),
                articuloPedido.getPrecioUnitario(),
                articuloPedido.getMontoTotal()
        );
    }
}
