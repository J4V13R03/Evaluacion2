package com.IngSoft.Evaluacion2.Costos;

import com.IngSoft.Evaluacion2.Mueble.Mueble;
import com.IngSoft.Evaluacion2.Variante.Variante;
import java.util.Set;

//Aqui se presenta el patron builder
public class PrecioBuilder {

    private Costos itemCotizable;

    public PrecioBuilder(Mueble muebleBase) {
        this.itemCotizable = new MuebleCotizado(muebleBase);
    }

    public PrecioBuilder conVariante(Variante variante) {
        this.itemCotizable = new VarianteDecorador(this.itemCotizable, variante);
        return this;
    }

    public PrecioBuilder conVariantes(Set<Variante> variantes) {
        for (Variante variante : variantes) {
            this.itemCotizable = new VarianteDecorador(this.itemCotizable, variante);
        }
        return this;
    }

    public Costos build() {
        return this.itemCotizable;
    }
}