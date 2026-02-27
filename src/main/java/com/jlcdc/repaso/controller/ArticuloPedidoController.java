package com.jlcdc.repaso.controller;

import com.jlcdc.repaso.dto.request.ArticuloPedidoRequest;
import com.jlcdc.repaso.dto.response.ArticuloPedidoResponse;
import com.jlcdc.repaso.model.ArticuloPedidoId;
import com.jlcdc.repaso.mapper.ArticuloPedidoMapper;
import com.jlcdc.repaso.service.ArticuloPedidoService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ArticuloPedidoResponse> crear(@RequestBody ArticuloPedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.crear(mapper.toModel(request))));
    }

    @GetMapping
    public ResponseEntity<List<ArticuloPedidoResponse>> listar() {
        List<ArticuloPedidoResponse> articulos = service.listar().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<ArticuloPedidoResponse>> obtenerPorPedido(@PathVariable Long idPedido) {
        List<ArticuloPedidoResponse> articulos = service.obtenerPorPedido(idPedido).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(articulos);
    }

    @PutMapping
    public ResponseEntity<ArticuloPedidoResponse> actualizar(@RequestBody ArticuloPedidoRequest request) {
        var articuloActualizado = mapper.toModel(request);
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(articuloActualizado.getId(), articuloActualizado)));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminar(@RequestParam Long idPedido, @RequestParam Long idArticulo) {
        ArticuloPedidoId id = new ArticuloPedidoId(idPedido, idArticulo);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
