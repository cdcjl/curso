package com.jlcdc.curso.repository;

import com.jlcdc.curso.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente_RutCliente(String rutCliente);
}
