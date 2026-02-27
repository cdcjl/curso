package com.jlcdc.curso.controller;

import com.jlcdc.curso.dto.ArticuloPedidoDTO;
import com.jlcdc.curso.model.ArticuloPedido;
import com.jlcdc.curso.model.ArticuloPedidoId;
import com.jlcdc.curso.model.Pedido;
import com.jlcdc.curso.service.ArticuloPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-pedidos")
public class ArticuloPedidoController {

    private final ArticuloPedidoService service;

    public ArticuloPedidoController(ArticuloPedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ArticuloPedido> crear(@RequestBody ArticuloPedidoDTO articuloDTO) {
        ArticuloPedido articuloPedido = convertirDTO(articuloDTO);
        ArticuloPedido nuevoArticulo = service.crear(articuloPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoArticulo);
    }

    @GetMapping
    public ResponseEntity<List<ArticuloPedido>> listar() {
        List<ArticuloPedido> articulos = service.listar();
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<ArticuloPedido>> obtenerPorPedido(@PathVariable Long idPedido) {
        List<ArticuloPedido> articulos = service.obtenerPorPedido(idPedido);
        return ResponseEntity.ok(articulos);
    }

    @PutMapping
    public ResponseEntity<ArticuloPedido> actualizar(@RequestBody ArticuloPedidoDTO articuloDTO) {
        ArticuloPedido articuloActualizado = convertirDTO(articuloDTO);
        ArticuloPedido articuloModificado = service.actualizar(articuloActualizado.getId(), articuloActualizado);
        return ResponseEntity.ok(articuloModificado);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminar(@RequestParam Long idPedido, @RequestParam Long idArticulo) {
        ArticuloPedidoId id = new ArticuloPedidoId(idPedido, idArticulo);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private ArticuloPedido convertirDTO(ArticuloPedidoDTO dto) {
        ArticuloPedido articuloPedido = new ArticuloPedido();
        articuloPedido.setId(new ArticuloPedidoId(dto.getIdPedido(), dto.getIdArticulo()));
        
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        articuloPedido.setPedido(pedido);
        
        articuloPedido.setCantidadPedida(dto.getCantidadPedida());
        articuloPedido.setPrecioUnitario(dto.getPrecioUnitario());
        return articuloPedido;
    }
}
