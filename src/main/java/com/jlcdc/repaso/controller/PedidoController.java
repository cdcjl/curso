package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.api.ApiResponse;
import com.jlcdc.repaso.dto.request.PedidoRequest;
import com.jlcdc.repaso.dto.response.PedidoResponse;
import com.jlcdc.repaso.mapper.PedidoMapper;
import com.jlcdc.repaso.service.PedidoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<PedidoResponse>> crear(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = mapper.toResponse(service.crear(mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Pedido creado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listar() {
        List<PedidoResponse> pedidos = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Listado de pedidos", pedidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> obtenerPorId(@PathVariable Long id) {
        PedidoResponse response = mapper.toResponse(service.obtenerPorId(id));
        return ResponseEntity.ok(ApiResponse.ok("Pedido obtenido", response));
    }

    @GetMapping("/cliente/{rut}")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> obtenerPorClienteRut(@PathVariable String rut) {
        List<PedidoResponse> pedidos = service.obtenerPorClienteRut(rut).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Pedidos por cliente", pedidos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = mapper.toResponse(service.actualizar(id, mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Pedido actualizado", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Pedido eliminado", null));
    }
}
