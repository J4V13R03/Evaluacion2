package com.IngSoft.Evaluacion2.Mueble;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "mueble")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMueble;

    @Column(nullable = false)
    private String nombreMueble;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Long precioBase;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String tamano;

    @Column(nullable = false)
    private String material;

    //  Getters y Setters
    
    public Mueble() {
    }

    public Long getIdMueble() { return idMueble; }
    public void setIdMueble(Long idMueble) { this.idMueble = idMueble; }
    public String getNombreMueble() { return nombreMueble; }
    public void setNombreMueble(String nombreMueble) { this.nombreMueble = nombreMueble; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getPrecioBase() { return precioBase; }
    public void setPrecioBase(Long precioBase) { this.precioBase = precioBase; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
}