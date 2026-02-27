package com.jlcdc.repaso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalPedidoDTO {

    private Long idSucursal;

    private String ciudad;

    private String nombreSucursal;

    private Long idPedido;

    private String rutCliente;

    private LocalDate fechaPedido;
}
