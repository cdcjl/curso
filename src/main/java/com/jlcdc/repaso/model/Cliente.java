package com.jlcdc.repaso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(name = "rut_cliente")
    private String rutCliente;

    @Column(name = "nombre_cliente")
    private String nombreCliente;
    
    @Column(name = "direccion_cliente")
    private String direccionCliente;
    
    @Column(name = "telefono_cliente")
    private String telefonoCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
}
