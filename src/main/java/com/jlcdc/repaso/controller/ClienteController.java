package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.ClienteRequest;
import com.jlcdc.repaso.dto.response.ClienteResponse;
import com.jlcdc.repaso.mapper.ClienteMapper;
import com.jlcdc.repaso.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    public ClienteController(ClienteService service, ClienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.crear(mapper.toModel(request))));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        List<ClienteResponse> clientes = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<ClienteResponse> obtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(mapper.toResponse(service.obtenerPorRut(rut)));
    }

    @PutMapping("/{rut}")
    public ResponseEntity<ClienteResponse> actualizar(@PathVariable String rut, @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(rut, mapper.toModel(request))));
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        service.eliminar(rut);
        return ResponseEntity.noContent().build();
    }
}
