package com.IngSoft.Evaluacion2.Cotizacion;

import com.IngSoft.Evaluacion2.Mueble.Mueble;
import com.IngSoft.Evaluacion2.Variante.Variante;
import jakarta.persistence.*;
// Agregamos esta importación para arreglar el error
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cotizacion_item")
public class CotizacionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precioItemCalculado;

    // muchos Items pertenecen a una Cotizacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cotizacion_id", nullable = false)
    @JsonIgnore
    private Cotizacion cotizacion;

    // cada Item esta asociado a un mueble
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mueble_id", nullable = false)
    private Mueble mueble;

    // un Item puede tener muchas variantes
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "cotizacion_item_variantes",
        joinColumns = @JoinColumn(name = "cotizacion_item_id"),
        inverseJoinColumns = @JoinColumn(name = "variante_id")
    )
    private Set<Variante> variantes = new HashSet<>();

    // --- Getters y Setters ---
    public Long getIdItem() { return idItem; }
    public void setIdItem(Long idItem) { this.idItem = idItem; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Integer getPrecioItemCalculado() { return precioItemCalculado; }
    public void setPrecioItemCalculado(Integer precioItemCalculado) { this.precioItemCalculado = precioItemCalculado; }
    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }
    public Mueble getMueble() { return mueble; }
    public void setMueble(Mueble mueble) { this.mueble = mueble; }
    public Set<Variante> getVariantes() { return variantes; }
    public void setVariantes(Set<Variante> variantes) { this.variantes = variantes; }
}