package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.api.ApiResponse;
import com.jlcdc.repaso.dto.request.ArticuloPedidoRequest;
import com.jlcdc.repaso.dto.response.ArticuloPedidoResponse;
import com.jlcdc.repaso.model.ArticuloPedidoId;
import com.jlcdc.repaso.mapper.ArticuloPedidoMapper;
import com.jlcdc.repaso.service.ArticuloPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-pedidos")
public class ArticuloPedidoController {

    private final ArticuloPedidoService service;
    private final ArticuloPedidoMapper mapper;

    public ArticuloPedidoController(ArticuloPedidoService service, ArticuloPedidoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ArticuloPedidoResponse>> crear(@Valid @RequestBody ArticuloPedidoRequest request) {
        ArticuloPedidoResponse response = mapper.toResponse(service.crear(mapper.toModel(request)));
        return ResponseEntity.ok(ApiResponse.ok("Articulo de pedido creado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticuloPedidoResponse>>> listar() {
        List<ArticuloPedidoResponse> articulos = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Listado de articulos de pedido", articulos));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<ApiResponse<List<ArticuloPedidoResponse>>> obtenerPorPedido(@PathVariable Long idPedido) {
        List<ArticuloPedidoResponse> articulos = service.obtenerPorPedido(idPedido).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(ApiResponse.ok("Articulos de pedido por pedido", articulos));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ArticuloPedidoResponse>> actualizar(@Valid @RequestBody ArticuloPedidoRequest request) {
        var articuloActualizado = mapper.toModel(request);
        ArticuloPedidoResponse response = mapper.toResponse(service.actualizar(articuloActualizado.getId(), articuloActualizado));
        return ResponseEntity.ok(ApiResponse.ok("Articulo de pedido actualizado", response));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> eliminar(@RequestParam Long idPedido, @RequestParam Long idArticulo) {
        ArticuloPedidoId id = new ArticuloPedidoId(idPedido, idArticulo);
        service.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Articulo de pedido eliminado", null));
    }
}
