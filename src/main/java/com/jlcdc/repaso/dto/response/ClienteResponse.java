package com.jlcdc.repaso.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private String rutCliente;
    private String nombreCliente;
    private String direccionCliente;
    private String telefonoCliente;
}
