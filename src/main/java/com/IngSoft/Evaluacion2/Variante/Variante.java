package com.IngSoft.Evaluacion2.Variante;

import jakarta.persistence.*;

@Entity
@Table(name = "variante")
public class Variante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVariante;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private Long costoAdicional;

    @Column(name = "Tipo_Compatible")
    private String tipoCompatible;


    public Variante() {
    }

    public Long getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(Long idVariante) {
        this.idVariante = idVariante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(Long costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    public String getTipoCompatible() { 
        return tipoCompatible; 
    }
    public void setTipoCompatible(String tipoCompatible) { 
        this.tipoCompatible = tipoCompatible; 
    }
}