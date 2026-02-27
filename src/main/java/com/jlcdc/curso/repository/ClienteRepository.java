package com.jlcdc.curso.repository;

import com.jlcdc.curso.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    boolean existsByTelefonoCliente(String telefonoCliente);
}
