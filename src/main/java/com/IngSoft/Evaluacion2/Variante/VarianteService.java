package com.IngSoft.Evaluacion2.Variante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VarianteService {

    @Autowired
    private VarianteRepository varianteRepository;

    // Crear o Actualizar
    public Variante crearVariante(Variante variante) {
        return varianteRepository.save(variante);
    }

    // Listar
    public List<Variante> obtenerTodasLasVariantes() {
        return varianteRepository.findAll();
    }

    // Obtener por ID
    public Optional<Variante> obtenerVariantePorId(Long id) {
        return varianteRepository.findById(id);
    }

    // Eliminar
    public void eliminarVariante(Long id) {
        varianteRepository.deleteById(id);
    }
}