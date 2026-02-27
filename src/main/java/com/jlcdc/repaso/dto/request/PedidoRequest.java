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
public class PedidoRequest {
    @NotBlank(message = "El rutCliente es obligatorio")
    @Size(max = 15, message = "El rutCliente no puede superar 15 caracteres")
    private String rutCliente;

    @NotNull(message = "El idSucursal es obligatorio")
    @Positive(message = "El idSucursal debe ser mayor a 0")
    private Long idSucursal;

    @NotNull(message = "La fechaPedido es obligatoria")
    private LocalDate fechaPedido;
}
