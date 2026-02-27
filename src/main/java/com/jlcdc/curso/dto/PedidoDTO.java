package com.jlcdc.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long idPedido;

    private String rutCliente;

    private Long idSucursal;

    private LocalDate fechaPedido;
}
