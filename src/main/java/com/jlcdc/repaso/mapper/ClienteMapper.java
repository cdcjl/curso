package com.jlcdc.repaso.mapper;

import com.jlcdc.repaso.dto.request.ClienteRequest;
import com.jlcdc.repaso.dto.response.ClienteResponse;
import com.jlcdc.repaso.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toModel(ClienteRequest dto) {
        Cliente cliente = new Cliente();
        cliente.setRutCliente(dto.getRutCliente());
        cliente.setNombreCliente(dto.getNombreCliente());
        cliente.setDireccionCliente(dto.getDireccionCliente());
        cliente.setTelefonoCliente(dto.getTelefonoCliente());
        return cliente;
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getRutCliente(),
                cliente.getNombreCliente(),
                cliente.getDireccionCliente(),
                cliente.getTelefonoCliente()
        );
    }
}
