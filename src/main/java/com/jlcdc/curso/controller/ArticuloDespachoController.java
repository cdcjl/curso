package com.jlcdc.curso.controller;

import com.jlcdc.curso.dto.ArticuloDespachoDTO;
import com.jlcdc.curso.model.ArticuloDespacho;
import com.jlcdc.curso.model.Pedido;
import com.jlcdc.curso.service.ArticuloDespachoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-despachos")
public class ArticuloDespachoController {

    private final ArticuloDespachoService service;

    public ArticuloDespachoController(ArticuloDespachoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ArticuloDespacho> crear(@RequestBody ArticuloDespachoDTO articuloDespachoDTO) {
        ArticuloDespacho articuloDespacho = convertirDTO(articuloDespachoDTO);
        ArticuloDespacho nuevoDespacho = service.crear(articuloDespacho);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDespacho);
    }

    @GetMapping
    public ResponseEntity<List<ArticuloDespacho>> listar() {
        List<ArticuloDespacho> despachos = service.listar();
        return ResponseEntity.ok(despachos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloDespacho> obtenerPorId(@PathVariable Long id) {
        ArticuloDespacho despacho = service.obtenerPorId(id);
        return ResponseEntity.ok(despacho);
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<ArticuloDespacho>> obtenerPorPedido(@PathVariable Long idPedido) {
        List<ArticuloDespacho> despachos = service.obtenerPorPedido(idPedido);
        return ResponseEntity.ok(despachos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticuloDespacho> actualizar(@PathVariable Long id, @RequestBody ArticuloDespachoDTO articuloDespachoDTO) {
        ArticuloDespacho articuloActualizado = convertirDTO(articuloDespachoDTO);
        ArticuloDespacho articuloModificado = service.actualizar(id, articuloActualizado);
        return ResponseEntity.ok(articuloModificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private ArticuloDespacho convertirDTO(ArticuloDespachoDTO dto) {
        ArticuloDespacho articuloDespacho = new ArticuloDespacho();
        articuloDespacho.setIdDespacho(dto.getIdDespacho());
        
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        articuloDespacho.setPedido(pedido);
        
        articuloDespacho.setIdArticulo(dto.getIdArticulo());
        articuloDespacho.setCodArticulo(dto.getCodArticulo());
        articuloDespacho.setCantidadDespachada(dto.getCantidadDespachada());
        articuloDespacho.setMontoDespachado(dto.getMontoDespachado());
        articuloDespacho.setFechaDespacho(dto.getFechaDespacho());
        return articuloDespacho;
    }
}
