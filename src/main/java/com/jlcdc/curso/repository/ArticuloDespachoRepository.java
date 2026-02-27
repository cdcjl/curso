package com.jlcdc.curso.repository;

import com.jlcdc.curso.model.ArticuloDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloDespachoRepository extends JpaRepository<ArticuloDespacho, Long> {
    List<ArticuloDespacho> findByPedido_IdPedido(Long idPedido);
}
