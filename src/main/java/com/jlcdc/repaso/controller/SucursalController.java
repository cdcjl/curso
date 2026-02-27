package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.SucursalPedidoDTO;
import com.jlcdc.repaso.dto.request.SucursalRequest;
import com.jlcdc.repaso.dto.response.SucursalResponse;
import com.jlcdc.repaso.mapper.SucursalMapper;
import com.jlcdc.repaso.service.SucursalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService service;
    private final SucursalMapper mapper;

    public SucursalController(SucursalService service, SucursalMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<SucursalResponse> crear(@RequestBody SucursalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.crear(mapper.toModel(request))));
    }

    @GetMapping
    public ResponseEntity<List<SucursalResponse>> listar() {
        List<SucursalResponse> sucursales = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/con-pedidos")
    public ResponseEntity<List<SucursalPedidoDTO>> listarSucursalConPedidos() {
        List<SucursalPedidoDTO> sucursales = service.listarSucursalConPedidos();
        return ResponseEntity.ok(sucursales);
    }   

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.obtenerPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponse> actualizar(@PathVariable Long id, @RequestBody SucursalRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(id, mapper.toModel(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
