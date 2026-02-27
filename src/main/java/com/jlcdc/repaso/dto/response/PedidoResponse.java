package com.jlcdc.repaso.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {
    private Long idPedido;
    private String rutCliente;
    private Long idSucursal;
    private LocalDate fechaPedido;
}
