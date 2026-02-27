package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.api.ApiResponse;
import com.jlcdc.repaso.dto.request.ArticuloDespachoRequest;
import com.jlcdc.repaso.dto.response.ArticuloDespachoResponse;
import com.jlcdc.repaso.mapper.ArticuloDespachoMapper;
import com.jlcdc.repaso.service.ArticuloDespachoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<ArticuloDespachoResponse>> crear(@Valid @RequestBody ArticuloDespachoRequest request) {
        ArticuloDespachoResponse response = mapper.toResponse(service.crear(mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Articulo de despacho creado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticuloDespachoResponse>>> listar() {
        List<ArticuloDespachoResponse> despachos = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Listado de articulos de despacho", despachos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticuloDespachoResponse>> obtenerPorId(@PathVariable Long id) {
        ArticuloDespachoResponse response = mapper.toResponse(service.obtenerPorId(id));
        return ResponseEntity.ok(ApiResponse.ok("Articulo de despacho obtenido", response));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<ApiResponse<List<ArticuloDespachoResponse>>> obtenerPorPedido(@PathVariable Long idPedido) {
        List<ArticuloDespachoResponse> despachos = service.obtenerPorPedido(idPedido).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Articulos de despacho por pedido", despachos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticuloDespachoResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody ArticuloDespachoRequest request) {
        ArticuloDespachoResponse response = mapper.toResponse(service.actualizar(id, mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Articulo de despacho actualizado", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Articulo de despacho eliminado", null));
    }
}
