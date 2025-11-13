package com.IngSoft.Evaluacion2.Costos; // <-- PAQUETE CORREGIDO

public abstract class MuebleDecorador implements Costos {
    
    protected Costos muebleEnvuelto;

    public MuebleDecorador(Costos muebleEnvuelto) {
        this.muebleEnvuelto = muebleEnvuelto;
    }

    @Override
    public Integer getPrecio() {
        return muebleEnvuelto.getPrecio();
    }
}