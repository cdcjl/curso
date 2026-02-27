package com.jlcdc.repaso.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloPedidoRequest {
    @NotNull(message = "El idPedido es obligatorio")
    @Positive(message = "El idPedido debe ser mayor a 0")
    private Long idPedido;

    @NotNull(message = "El idArticulo es obligatorio")
    @Positive(message = "El idArticulo debe ser mayor a 0")
    private Long idArticulo;

    @NotNull(message = "La cantidadPedida es obligatoria")
    @Positive(message = "La cantidadPedida debe ser mayor a 0")
    private Integer cantidadPedida;

    @NotNull(message = "El precioUnitario es obligatorio")
    @Positive(message = "El precioUnitario debe ser mayor a 0")
    private Double precioUnitario;
}
