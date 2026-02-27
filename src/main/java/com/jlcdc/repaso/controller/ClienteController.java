package com.jlcdc.repaso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jlcdc.repaso.dto.ClienteDTO;
import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = convertirDTO(clienteDTO);
        Cliente nuevoCliente = service.crear(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = service.listar();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Cliente> obtenerPorRut(@PathVariable String rut) {
        Cliente cliente = service.obtenerPorRut(rut);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<Cliente> actualizar(@PathVariable String rut, @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteActualizado = convertirDTO(clienteDTO);
        Cliente clienteModificado = service.actualizar(rut, clienteActualizado);
        return ResponseEntity.ok(clienteModificado);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        service.eliminar(rut);
        return ResponseEntity.noContent().build();
    }

    private Cliente convertirDTO(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setRutCliente(dto.getRutCliente());
        cliente.setNombreCliente(dto.getNombreCliente());
        cliente.setDireccionCliente(dto.getDireccionCliente());
        cliente.setTelefonoCliente(dto.getTelefonoCliente());
        return cliente;
    }
}
