package com.jlcdc.repaso.repository.projection;

import java.time.LocalDate;

public interface SucursalPedidoProjection {
    Long getIdSucursal();
    String getCiudad();
    String getNombreSucursal();
    Long getIdPedido();
    String getRutCliente();
    LocalDate getFechaPedido();
}
