package com.IngSoft.Evaluacion2.Costos; // <-- PAQUETE CORREGIDO

public abstract class MuebleDecorador implements Costos {
    
    protected Costos muebleEnvuelto;

    public MuebleDecorador(Costos muebleEnvuelto) {
        this.muebleEnvuelto = muebleEnvuelto;
    }

    @Override
    public Long getPrecio() {
        return muebleEnvuelto.getPrecio();
    }
}