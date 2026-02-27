package com.jlcdc.curso.service;

import com.jlcdc.curso.model.ArticuloPedido;
import com.jlcdc.curso.model.ArticuloPedidoId;
import com.jlcdc.curso.repository.ArticuloPedidoRepository;
import com.jlcdc.curso.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloPedidoService {

    private final ArticuloPedidoRepository repository;
    private final PedidoRepository pedidoRepository;

    public ArticuloPedidoService(ArticuloPedidoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public ArticuloPedido crear(ArticuloPedido articuloPedido) {
        // Validar que el pedido existe
        pedidoRepository.findById(articuloPedido.getId().getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (articuloPedido.getCantidadPedida() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        if (articuloPedido.getPrecioUnitario() < 0) {
            throw new RuntimeException("El precio unitario no puede ser negativo");
        }

        return repository.save(articuloPedido);
    }

    public List<ArticuloPedido> listar() {
        return repository.findAll();
    }

    public ArticuloPedido obtenerPorId(ArticuloPedidoId id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo de pedido no encontrado"));
    }

    public List<ArticuloPedido> obtenerPorPedido(Long idPedido) {
        return repository.findByPedido_IdPedido(idPedido);
    }

    public ArticuloPedido actualizar(ArticuloPedidoId id, ArticuloPedido articuloActualizado) {
        ArticuloPedido articuloExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo de pedido no encontrado"));

        if (articuloActualizado.getCantidadPedida() > 0) {
            articuloExistente.setCantidadPedida(articuloActualizado.getCantidadPedida());
        }

        if (articuloActualizado.getPrecioUnitario() >= 0) {
            articuloExistente.setPrecioUnitario(articuloActualizado.getPrecioUnitario());
        }

        return repository.save(articuloExistente);
    }

    public void eliminar(ArticuloPedidoId id) {
        repository.deleteById(id);
    }
}
