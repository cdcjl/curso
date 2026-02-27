package com.jlcdc.curso.service;

import com.jlcdc.curso.model.Sucursal;
import com.jlcdc.curso.repository.SucursalRepository;
import org.springframework.stereotype.Service;

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
}
