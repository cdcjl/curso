package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.PedidoRequest;
import com.jlcdc.repaso.dto.response.PedidoResponse;
import com.jlcdc.repaso.mapper.PedidoMapper;
import com.jlcdc.repaso.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

    public PedidoController(PedidoService service, PedidoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.crear(mapper.toModel(request))));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        List<PedidoResponse> pedidos = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.obtenerPorId(id)));
    }

    @GetMapping("/cliente/{rut}")
    public ResponseEntity<List<PedidoResponse>> obtenerPorClienteRut(@PathVariable String rut) {
        List<PedidoResponse> pedidos = service.obtenerPorClienteRut(rut).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> actualizar(@PathVariable Long id, @RequestBody PedidoRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(id, mapper.toModel(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
