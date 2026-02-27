package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.api.ApiResponse;
import com.jlcdc.repaso.dto.SucursalPedidoDTO;
import com.jlcdc.repaso.dto.request.SucursalRequest;
import com.jlcdc.repaso.dto.response.SucursalResponse;
import com.jlcdc.repaso.mapper.SucursalMapper;
import com.jlcdc.repaso.service.SucursalService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<SucursalResponse>> crear(@Valid @RequestBody SucursalRequest request) {
        SucursalResponse response = mapper.toResponse(service.crear(mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Sucursal creada", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SucursalResponse>>> listar() {
        List<SucursalResponse> sucursales = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Listado de sucursales", sucursales));
    }

    @GetMapping("/con-pedidos")
    public ResponseEntity<ApiResponse<List<SucursalPedidoDTO>>> listarSucursalConPedidos() {
        List<SucursalPedidoDTO> sucursales = service.listarSucursalConPedidos();
        return ResponseEntity.ok(ApiResponse.ok("Listado de sucursales con pedidos", sucursales));
    }   

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SucursalResponse>> obtenerPorId(@PathVariable Long id) {
        SucursalResponse response = mapper.toResponse(service.obtenerPorId(id));
        return ResponseEntity.ok(ApiResponse.ok("Sucursal obtenida", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SucursalResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody SucursalRequest request) {
        SucursalResponse response = mapper.toResponse(service.actualizar(id, mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Sucursal actualizada", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Sucursal eliminada", null));
    }
}
