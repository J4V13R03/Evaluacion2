package com.IngSoft.Evaluacion2.Mueble;
import jakarta.persistence.*;

@Entity
@Table(name = "mueble")
public class Mueble {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMueble;

    @Column(nullable = false)
    private String nombreMueble;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Integer precioBase;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String tamano;

    @Column(nullable = false)
    private String material;

    
    public Mueble() {
    }

    //Getters y Setters
    public Long getIdMueble() { return idMueble; }
    public void setIdMueble(Long idMueble) { this.idMueble = idMueble; }
    public String getNombreMueble() { return nombreMueble; }
    public void setNombreMueble(String nombreMueble) { this.nombreMueble = nombreMueble; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getPrecioBase() { return precioBase; }
    public void setPrecioBase(Integer precioBase) { this.precioBase = precioBase; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
}
