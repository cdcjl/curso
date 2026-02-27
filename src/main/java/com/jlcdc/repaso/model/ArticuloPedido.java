package com.jlcdc.repaso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articulos_pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloPedido {

    @EmbeddedId
    private ArticuloPedidoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", insertable = false, updatable = false)
    private Pedido pedido;

    @Column(name = "cantidad_pedida")
    private Integer cantidadPedida;
    
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    public Double getMontoTotal() {
        return cantidadPedida != null && precioUnitario != null 
            ? cantidadPedida * precioUnitario 
            : 0.0;
    }
}
