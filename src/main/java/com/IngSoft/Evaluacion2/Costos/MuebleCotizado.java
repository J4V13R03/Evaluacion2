package com.IngSoft.Evaluacion2.Costos; // <-- PAQUETE CORREGIDO

import com.IngSoft.Evaluacion2.Mueble.Mueble;

public class MuebleCotizado implements Costos {

    private Mueble mueble;

    public MuebleCotizado(Mueble mueble) {
        this.mueble = mueble;
    }

    @Override
    public Integer getPrecio() {
        return this.mueble.getPrecioBase();
    }
}