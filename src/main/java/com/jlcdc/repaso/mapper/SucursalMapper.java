package com.jlcdc.repaso.mapper;

import com.jlcdc.repaso.dto.request.SucursalRequest;
import com.jlcdc.repaso.dto.response.SucursalResponse;
import com.jlcdc.repaso.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal toModel(SucursalRequest dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal(dto.getNombreSucursal());
        sucursal.setCiudad(dto.getCiudad());
        return sucursal;
    }

    public SucursalResponse toResponse(Sucursal sucursal) {
        return new SucursalResponse(
                sucursal.getIdSucursal(),
                sucursal.getNombreSucursal(),
                sucursal.getCiudad()
        );
    }
}
