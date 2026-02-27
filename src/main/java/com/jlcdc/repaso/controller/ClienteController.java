package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.api.ApiResponse;
import com.jlcdc.repaso.dto.request.ClienteRequest;
import com.jlcdc.repaso.dto.response.ClienteResponse;
import com.jlcdc.repaso.mapper.ClienteMapper;
import com.jlcdc.repaso.service.ClienteService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<ClienteResponse>> crear(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = mapper.toResponse(service.crear(mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Cliente creado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> listar() {
        List<ClienteResponse> clientes = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Listado de clientes", clientes));
    }

    @GetMapping("/{rut}")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerPorRut(@PathVariable String rut) {
        ClienteResponse response = mapper.toResponse(service.obtenerPorRut(rut));
        return ResponseEntity.ok(ApiResponse.ok("Cliente obtenido", response));
    }

    @PutMapping("/{rut}")
    public ResponseEntity<ApiResponse<ClienteResponse>> actualizar(@PathVariable String rut, @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = mapper.toResponse(service.actualizar(rut, mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Cliente actualizado", response));
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable String rut) {
        service.eliminar(rut);
        return ResponseEntity.ok(ApiResponse.ok("Cliente eliminado", null));
    }
}
