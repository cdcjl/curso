package com.jlcdc.repaso.mapper;

import com.jlcdc.repaso.dto.request.ArticuloDespachoRequest;
import com.jlcdc.repaso.dto.response.ArticuloDespachoResponse;
import com.jlcdc.repaso.model.ArticuloDespacho;
import com.jlcdc.repaso.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class ArticuloDespachoMapper {

    public ArticuloDespacho toModel(ArticuloDespachoRequest dto) {
        ArticuloDespacho articuloDespacho = new ArticuloDespacho();

        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        articuloDespacho.setPedido(pedido);

        articuloDespacho.setIdArticulo(dto.getIdArticulo());
        articuloDespacho.setCodArticulo(dto.getCodArticulo());
        articuloDespacho.setCantidadDespachada(dto.getCantidadDespachada());
        articuloDespacho.setMontoDespachado(dto.getMontoDespachado());
        articuloDespacho.setFechaDespacho(dto.getFechaDespacho());
        return articuloDespacho;
    }

    public ArticuloDespachoResponse toResponse(ArticuloDespacho despacho) {
        return new ArticuloDespachoResponse(
                despacho.getIdDespacho(),
                despacho.getPedido() != null ? despacho.getPedido().getIdPedido() : null,
                despacho.getIdArticulo(),
                despacho.getCodArticulo(),
                despacho.getCantidadDespachada(),
                despacho.getMontoDespachado(),
                despacho.getFechaDespacho()
        );
    }
}
