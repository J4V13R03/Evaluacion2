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
    private Integer costoAdicional;

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

    public Integer getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(Integer costoAdicional) {
        this.costoAdicional = costoAdicional;
    }
}