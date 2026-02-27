package com.jlcdc.repaso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloPedidoRequest {
    private Long idPedido;
    private Long idArticulo;
    private Integer cantidadPedida;
    private Double precioUnitario;
}
