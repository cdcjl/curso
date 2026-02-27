package com.jlcdc.repaso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequest {
    private String nombreSucursal;
    private String ciudad;
}
