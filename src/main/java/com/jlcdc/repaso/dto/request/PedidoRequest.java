package com.jlcdc.repaso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    private String rutCliente;
    private Long idSucursal;
    private LocalDate fechaPedido;
}
