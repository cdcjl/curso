package com.jlcdc.repaso.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloPedidoResponse {
    private Long idPedido;
    private Long idArticulo;
    private Integer cantidadPedida;
    private Double precioUnitario;
    private Double montoTotal;
}
