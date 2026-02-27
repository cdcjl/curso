package com.jlcdc.repaso.service;

import org.springframework.stereotype.Service;

import com.jlcdc.repaso.model.ArticuloDespacho;
import com.jlcdc.repaso.repository.ArticuloDespachoRepository;
import com.jlcdc.repaso.repository.PedidoRepository;

import java.util.List;

@Service
public class ArticuloDespachoService {

    private final ArticuloDespachoRepository repository;
    private final PedidoRepository pedidoRepository;

    public ArticuloDespachoService(ArticuloDespachoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public ArticuloDespacho crear(ArticuloDespacho articuloDespacho) {
        // Validar que el pedido existe
        pedidoRepository.findById(articuloDespacho.getPedido().getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (articuloDespacho.getCantidadDespachada() <= 0) {
            throw new RuntimeException("La cantidad despachada debe ser mayor a 0");
        }

        if (articuloDespacho.getMontoDespachado() < 0) {
            throw new RuntimeException("El monto despachado no puede ser negativo");
        }

        return repository.save(articuloDespacho);
    }

    public List<ArticuloDespacho> listar() {
        return repository.findAll();
    }

    public ArticuloDespacho obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho con ID " + id + " no encontrado"));
    }

    public List<ArticuloDespacho> obtenerPorPedido(Long idPedido) {
        return repository.findByPedido_IdPedido(idPedido);
    }

    public ArticuloDespacho actualizar(Long id, ArticuloDespacho articuloActualizado) {
        ArticuloDespacho articuloExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho con ID " + id + " no encontrado"));

        if (articuloActualizado.getCantidadDespachada() > 0) {
            articuloExistente.setCantidadDespachada(articuloActualizado.getCantidadDespachada());
        }

        if (articuloActualizado.getMontoDespachado() >= 0) {
            articuloExistente.setMontoDespachado(articuloActualizado.getMontoDespachado());
        }

        if (articuloActualizado.getFechaDespacho() != null) {
            articuloExistente.setFechaDespacho(articuloActualizado.getFechaDespacho());
        }

        return repository.save(articuloExistente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
