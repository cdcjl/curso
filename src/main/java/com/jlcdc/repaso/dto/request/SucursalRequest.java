package com.jlcdc.repaso.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequest {
    @NotBlank(message = "El nombreSucursal es obligatorio")
    @Size(max = 50, message = "El nombreSucursal no puede superar 50 caracteres")
    private String nombreSucursal;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede superar 50 caracteres")
    private String ciudad;
}
