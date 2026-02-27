package com.jlcdc.repaso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    private String rutCliente;
    private String nombreCliente;
    private String direccionCliente;
    private String telefonoCliente;
}
