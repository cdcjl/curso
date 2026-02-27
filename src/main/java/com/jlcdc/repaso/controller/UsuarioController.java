package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.UsuarioRequest;
import com.jlcdc.repaso.dto.response.UsuarioResponse;
import com.jlcdc.repaso.mapper.UsuarioMapper;
import com.jlcdc.repaso.service.UsuarioService;
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
    public UsuarioResponse crear(@RequestBody UsuarioRequest request) {
        return mapper.toResponse(service.crear(mapper.toModel(request)));
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse obtener(@PathVariable Long id) {
        return mapper.toResponse(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        return mapper.toResponse(service.actualizar(id, mapper.toModel(request)));
    }
}
