package com.IngSoft.Evaluacion2.Variante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VarianteService {

    @Autowired
    private VarianteRepository varianteRepository;

    public Variante crearVariante(Variante variante) {
        return varianteRepository.save(variante);
    }

    public List<Variante> obtenerTodasLasVariantes() {
        return varianteRepository.findAll();
    }
}