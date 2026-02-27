package com.jlcdc.repaso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDespachoRequest {
    private Long idPedido;
    private Long idArticulo;
    private String codArticulo;
    private Integer cantidadDespachada;
    private Double montoDespachado;
    private LocalDate fechaDespacho;
}
