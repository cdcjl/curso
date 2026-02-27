package com.jlcdc.repaso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArticuloPedidoDTO {

    private Long idPedido;

    private Long idArticulo;

    private Integer cantidadPedida;

    private Double precioUnitario;
}
