package com.jlcdc.repaso.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloPedidoId implements Serializable {
    @Column(name = "id_pedido")
    private Long idPedido;
    
    @Column(name = "id_articulo")
    private Long idArticulo;
}
