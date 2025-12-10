package com.IngSoft.Evaluacion2.Costos; 

import com.IngSoft.Evaluacion2.Variante.Variante;

public class VarianteDecorador extends MuebleDecorador {
    
    private Variante variante;

    public VarianteDecorador(Costos muebleEnvuelto, Variante variante) {
        super(muebleEnvuelto); 
        this.variante = variante;
    }

    @Override
    public Long getPrecio() {
        Long precioBase = super.getPrecio();
        Long precioConVariante = precioBase + this.variante.getCostoAdicional();
        return precioConVariante;
    }
}