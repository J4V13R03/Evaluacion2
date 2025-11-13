package com.IngSoft.Evaluacion2.Costos; 

import com.IngSoft.Evaluacion2.Variante.Variante;

public class VarianteDecorador extends MuebleDecorador {
    
    private Variante variante;

    public VarianteDecorador(Costos muebleEnvuelto, Variante variante) {
        super(muebleEnvuelto); 
        this.variante = variante;
    }

    @Override
    public Integer getPrecio() {
        Integer precioBase = super.getPrecio();
        Integer precioConVariante = precioBase + this.variante.getCostoAdicional();
        return precioConVariante;
    }
}