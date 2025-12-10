package com.IngSoft.Evaluacion2.Cotizacion;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCotizacion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String estado; 

    @Column(nullable = false)
    private Long totalCalculado;

    // Una Cotización tiene muchos Items
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CotizacionItem> items = new ArrayList<>();

    // --- Getters y Setters ---
    public Long getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(Long idCotizacion) { this.idCotizacion = idCotizacion; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Long getTotalCalculado() { return totalCalculado; }
    public void setTotalCalculado(Long totalCalculado) { this.totalCalculado = totalCalculado; }
    public List<CotizacionItem> getItems() { return items; }
    public void setItems(List<CotizacionItem> items) { this.items = items; }

    public void addItem(CotizacionItem item) {
        this.items.add(item);
        item.setCotizacion(this);
    }
}