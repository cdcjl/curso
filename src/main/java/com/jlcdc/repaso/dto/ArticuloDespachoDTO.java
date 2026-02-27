package com.jlcdc.repaso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDespachoDTO {

    private Long idDespacho;

    private Long idPedido;

    private Long idArticulo;

    private String codArticulo;

    private Integer cantidadDespachada;

    private Double montoDespachado;

    private LocalDate fechaDespacho;
}
