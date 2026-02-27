package com.jlcdc.repaso.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    @NotBlank(message = "El rutCliente es obligatorio")
    @Size(max = 15, message = "El rutCliente no puede superar 15 caracteres")
    private String rutCliente;

    @NotBlank(message = "El nombreCliente es obligatorio")
    @Size(max = 200, message = "El nombreCliente no puede superar 200 caracteres")
    private String nombreCliente;

    @NotBlank(message = "La direccionCliente es obligatoria")
    @Size(max = 200, message = "La direccionCliente no puede superar 200 caracteres")
    private String direccionCliente;

    @NotBlank(message = "El telefonoCliente es obligatorio")
    @Size(max = 20, message = "El telefonoCliente no puede superar 20 caracteres")
    private String telefonoCliente;
}
