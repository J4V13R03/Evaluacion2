package com.IngSoft.Evaluacion2.Variante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/variantes")
public class VarianteController {

    @Autowired
    private VarianteService varianteService;

    @PostMapping
    public ResponseEntity<Variante> crearVariante(@RequestBody Variante variante) {
        return new ResponseEntity<>(varianteService.crearVariante(variante), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Variante>> listarVariantes() {
        return new ResponseEntity<>(varianteService.obtenerTodasLasVariantes(), HttpStatus.OK);
    }
}