package com.jlcdc.repaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlcdc.repaso.model.ArticuloDespacho;

import java.util.List;

@Repository
public interface ArticuloDespachoRepository extends JpaRepository<ArticuloDespacho, Long> {
    List<ArticuloDespacho> findByPedido_IdPedido(Long idPedido);
}
