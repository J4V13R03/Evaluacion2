package com.IngSoft.Evaluacion2.Mueble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuebleService {

    @Autowired
    private MuebleRepository muebleRepository;

    // crear
    public Mueble crearMueble(Mueble mueble) {
        mueble.setEstado("activo");
        return muebleRepository.save(mueble);
    }

    // leer
    public List<Mueble> obtenerTodosLosMuebles() {
        return muebleRepository.findAll();
    }

    public Optional<Mueble> obtenerMueblePorId(Long id) {
        return muebleRepository.findById(id);
    }

    // actualizar
    public Mueble actualizarMueble(Long id, Mueble muebleActualizado) {
        return muebleRepository.findById(id)
            .map(mueble -> {
                mueble.setNombreMueble(muebleActualizado.getNombreMueble());
                mueble.setTipo(muebleActualizado.getTipo());
                mueble.setPrecioBase(muebleActualizado.getPrecioBase());
                mueble.setStock(muebleActualizado.getStock());
                mueble.setEstado(muebleActualizado.getEstado());
                mueble.setTamano(muebleActualizado.getTamano());
                mueble.setMaterial(muebleActualizado.getMaterial());
                return muebleRepository.save(mueble);
            })
            .orElse(null); 
    }

    // deactivar
    public Mueble desactivarMueble(Long id) {
        return muebleRepository.findById(id)
            .map(mueble -> {
                mueble.setEstado("inactivo");
                return muebleRepository.save(mueble);
            })
            .orElse(null);
    }
    // activar
    public void activarMueble(Long id) {
        muebleRepository.findById(id).ifPresent(mueble -> {
            mueble.setEstado("activo");
            muebleRepository.save(mueble);
        });
    }
}