package com.jlcdc.repaso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "articulos_despachados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho")
    private Long idDespacho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "id_articulo")
    private Long idArticulo;
    
    @Column(name = "cod_articulo")
    private String codArticulo;
   
    @Column(name = "cantidad_despachada")
    private Integer cantidadDespachada;
    
    @Column(name = "monto_despachado")
    private Double montoDespachado;
    
    @Column(name = "fecha_despacho")
    private LocalDate fechaDespacho;   
}
