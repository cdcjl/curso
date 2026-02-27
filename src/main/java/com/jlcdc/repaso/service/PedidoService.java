package com.jlcdc.repaso.service;

import org.springframework.stereotype.Service;

import com.jlcdc.repaso.model.Pedido;
import com.jlcdc.repaso.repository.ClienteRepository;
import com.jlcdc.repaso.repository.PedidoRepository;
import com.jlcdc.repaso.repository.SucursalRepository;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final SucursalRepository sucursalRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, 
                        SucursalRepository sucursalRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.sucursalRepository = sucursalRepository;
    }

    public Pedido crear(Pedido pedido) {
        // Validar que el cliente existe
        clienteRepository.findById(pedido.getCliente().getRutCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Validar que la sucursal existe
        sucursalRepository.findById(pedido.getSucursal().getIdSucursal())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado"));
    }

    public List<Pedido> obtenerPorClienteRut(String rut) {
        return pedidoRepository.findByCliente_RutCliente(rut);
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado"));

        pedidoExistente.setFechaPedido(pedidoActualizado.getFechaPedido());

        return pedidoRepository.save(pedidoExistente);
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
