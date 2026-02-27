package com.jlcdc.repaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlcdc.repaso.model.ArticuloPedido;
import com.jlcdc.repaso.model.ArticuloPedidoId;

import java.util.List;

@Repository
public interface ArticuloPedidoRepository extends JpaRepository<ArticuloPedido, ArticuloPedidoId> {
    List<ArticuloPedido> findByPedido_IdPedido(Long idPedido);
}
