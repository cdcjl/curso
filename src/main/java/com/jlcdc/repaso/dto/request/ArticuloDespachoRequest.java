package com.jlcdc.repaso.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDespachoRequest {
    @NotNull(message = "El idPedido es obligatorio")
    @Positive(message = "El idPedido debe ser mayor a 0")
    private Long idPedido;

    @NotNull(message = "El idArticulo es obligatorio")
    @Positive(message = "El idArticulo debe ser mayor a 0")
    private Long idArticulo;

    @NotBlank(message = "El codArticulo es obligatorio")
    @Size(max = 50, message = "El codArticulo no puede superar 50 caracteres")
    private String codArticulo;

    @NotNull(message = "La cantidadDespachada es obligatoria")
    @Positive(message = "La cantidadDespachada debe ser mayor a 0")
    private Integer cantidadDespachada;

    @NotNull(message = "El montoDespachado es obligatorio")
    @Positive(message = "El montoDespachado debe ser mayor a 0")
    private Double montoDespachado;

    @NotNull(message = "La fechaDespacho es obligatoria")
    private LocalDate fechaDespacho;
}
