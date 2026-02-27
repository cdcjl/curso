package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.api.ApiResponse;
import com.jlcdc.repaso.dto.request.UsuarioRequest;
import com.jlcdc.repaso.dto.response.UsuarioResponse;
import com.jlcdc.repaso.mapper.UsuarioMapper;
import com.jlcdc.repaso.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    public UsuarioController(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponse>> crear(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = mapper.toResponse(service.crear(mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Usuario creado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listar() {
        List<UsuarioResponse> data = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Listado de usuarios", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> obtener(@PathVariable Long id) {
        UsuarioResponse response = mapper.toResponse(service.obtenerPorId(id));
        return ResponseEntity.ok(ApiResponse.ok("Usuario obtenido", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario eliminado", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = mapper.toResponse(service.actualizar(id, mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Usuario actualizado", response));
    }
}
