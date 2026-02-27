package com.jlcdc.repaso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @Column(name = "nombre_sucursal")
    private String nombreSucursal;
    
    @Column(name = "ciudad")
    private String ciudad;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
}
