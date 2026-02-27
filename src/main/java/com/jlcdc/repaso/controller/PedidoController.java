package com.jlcdc.repaso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jlcdc.repaso.dto.PedidoDTO;
import com.jlcdc.repaso.model.Cliente;
import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = convertirDTO(pedidoDTO);
        Pedido nuevoPedido = service.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = service.listar();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        Pedido pedido = service.obtenerPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/cliente/{rut}")
    public ResponseEntity<List<Pedido>> obtenerPorClienteRut(@PathVariable String rut) {
        List<Pedido> pedidos = service.obtenerPorClienteRut(rut);
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoActualizado = convertirDTO(pedidoDTO);
        Pedido pedidoModificado = service.actualizar(id, pedidoActualizado);
        return ResponseEntity.ok(pedidoModificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Pedido convertirDTO(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(dto.getIdPedido());
        
        Cliente cliente = new Cliente();
        cliente.setRutCliente(dto.getRutCliente());
        pedido.setCliente(cliente);
        
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(dto.getIdSucursal());
        pedido.setSucursal(sucursal);
        
        pedido.setFechaPedido(dto.getFechaPedido());
        return pedido;
    }
}
