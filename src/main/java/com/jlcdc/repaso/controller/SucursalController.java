package com.jlcdc.repaso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jlcdc.repaso.dto.SucursalDTO;
import com.jlcdc.repaso.dto.SucursalPedidoDTO;
import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.service.SucursalService;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final SucursalService service;

    public SucursalController(SucursalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Sucursal> crear(@RequestBody SucursalDTO sucursalDTO) {
        Sucursal sucursal = convertirDTO(sucursalDTO);
        Sucursal nuevaSucursal = service.crear(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
    }

    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> sucursales = service.listar();
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/con-pedidos")
    public ResponseEntity<List<SucursalPedidoDTO>> listarSucursalConPedidos() {
        List<SucursalPedidoDTO> sucursales = service.listarSucursalConPedidos();
        return ResponseEntity.ok(sucursales);
    }   

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerPorId(@PathVariable Long id) {
        Sucursal sucursal = service.obtenerPorId(id);
        return ResponseEntity.ok(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @RequestBody SucursalDTO sucursalDTO) {
        Sucursal sucursalActualizada = convertirDTO(sucursalDTO);
        Sucursal sucursalModificada = service.actualizar(id, sucursalActualizada);
        return ResponseEntity.ok(sucursalModificada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Sucursal convertirDTO(SucursalDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(dto.getIdSucursal());
        sucursal.setNombreSucursal(dto.getNombreSucursal());
        sucursal.setCiudad(dto.getCiudad());
        return sucursal;
    }
}
