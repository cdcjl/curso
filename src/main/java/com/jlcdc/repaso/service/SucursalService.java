package com.jlcdc.repaso.service;

import org.springframework.stereotype.Service;

import com.jlcdc.repaso.dto.SucursalPedidoDTO;
import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.repository.SucursalRepository;
import com.jlcdc.repaso.repository.projection.SucursalPedidoProjection;

import java.util.List;

@Service
public class SucursalService {

    private final SucursalRepository repository;

    public SucursalService(SucursalRepository repository) {
        this.repository = repository;
    }

    public Sucursal crear(Sucursal sucursal) {
        return repository.save(sucursal);
    }

    public List<Sucursal> listar() {
        return repository.findAll();
    }

    public List<SucursalPedidoDTO> listarSucursalConPedidos() {
        return repository.listarSucursalConPedidos()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public Sucursal obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + id + " no encontrada"));
    }

    public Sucursal actualizar(Long id, Sucursal sucursalActualizada) {
        Sucursal sucursalExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal con ID " + id + " no encontrada"));

        sucursalExistente.setNombreSucursal(sucursalActualizada.getNombreSucursal());
        sucursalExistente.setCiudad(sucursalActualizada.getCiudad());

        return repository.save(sucursalExistente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private SucursalPedidoDTO toDto(SucursalPedidoProjection projection) {
        return new SucursalPedidoDTO(
                projection.getIdSucursal(),
                projection.getCiudad(),
                projection.getNombreSucursal(),
                projection.getIdPedido(),
                projection.getRutCliente(),
                projection.getFechaPedido()
        );
    }
}
