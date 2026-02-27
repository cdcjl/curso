package com.jlcdc.curso.repository;

import com.jlcdc.curso.model.ArticuloPedido;
import com.jlcdc.curso.model.ArticuloPedidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloPedidoRepository extends JpaRepository<ArticuloPedido, ArticuloPedidoId> {
    List<ArticuloPedido> findByPedido_IdPedido(Long idPedido);
}
