package com.jlcdc.repaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jlcdc.repaso.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    boolean existsByTelefonoCliente(String telefonoCliente);
}
