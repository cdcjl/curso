package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.ArticuloDespachoRequest;
import com.jlcdc.repaso.dto.response.ArticuloDespachoResponse;
import com.jlcdc.repaso.mapper.ArticuloDespachoMapper;
import com.jlcdc.repaso.service.ArticuloDespachoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-despachos")
public class ArticuloDespachoController {

    private final ArticuloDespachoService service;
    private final ArticuloDespachoMapper mapper;

    public ArticuloDespachoController(ArticuloDespachoService service, ArticuloDespachoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ArticuloDespachoResponse> crear(@RequestBody ArticuloDespachoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.crear(mapper.toModel(request))));
    }

    @GetMapping
    public ResponseEntity<List<ArticuloDespachoResponse>> listar() {
        List<ArticuloDespachoResponse> despachos = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(despachos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloDespachoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.obtenerPorId(id)));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<ArticuloDespachoResponse>> obtenerPorPedido(@PathVariable Long idPedido) {
        List<ArticuloDespachoResponse> despachos = service.obtenerPorPedido(idPedido).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(despachos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticuloDespachoResponse> actualizar(@PathVariable Long id, @RequestBody ArticuloDespachoRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(id, mapper.toModel(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
