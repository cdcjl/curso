package com.jlcdc.repaso.service;

import org.springframework.stereotype.Service;

import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente crear(Cliente cliente) {
        if (repository.existsById(cliente.getRutCliente())) {
            throw new RuntimeException("Cliente con RUT " + cliente.getRutCliente() + " ya existe");
        }
        return repository.save(cliente);
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente obtenerPorRut(String rut) {
        return repository.findById(rut)
                .orElseThrow(() -> new RuntimeException("Cliente con RUT " + rut + " no encontrado"));
    }

    public Cliente actualizar(String rut, Cliente clienteActualizado) {
        Cliente clienteExistente = repository.findById(rut)
                .orElseThrow(() -> new RuntimeException("Cliente con RUT " + rut + " no encontrado"));

        clienteExistente.setNombreCliente(clienteActualizado.getNombreCliente());
        clienteExistente.setDireccionCliente(clienteActualizado.getDireccionCliente());
        clienteExistente.setTelefonoCliente(clienteActualizado.getTelefonoCliente());

        return repository.save(clienteExistente);
    }

    public void eliminar(String rut) {
        repository.deleteById(rut);
    }
}
