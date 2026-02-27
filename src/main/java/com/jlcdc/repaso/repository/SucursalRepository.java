package com.jlcdc.repaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jlcdc.repaso.model.Sucursal;
import com.jlcdc.repaso.repository.projection.SucursalPedidoProjection;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    List<Sucursal> findByCiudad(String ciudad);

    @Query(value = """
            SELECT s.id_sucursal AS idSucursal,
                   s.ciudad AS ciudad,
                   s.nombre_sucursal AS nombreSucursal,
                   p.id_pedido AS idPedido,
                   p.rut_cliente AS rutCliente,
                   p.fecha_pedido AS fechaPedido
            FROM sucursal s
            LEFT JOIN pedidos p ON s.id_sucursal = p.id_sucursal
            """, nativeQuery = true)
    List<SucursalPedidoProjection> listarSucursalConPedidos();
}
