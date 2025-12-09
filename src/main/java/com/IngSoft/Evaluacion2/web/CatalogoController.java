package com.IngSoft.Evaluacion2.web;

import com.IngSoft.Evaluacion2.Mueble.MuebleService;
import com.IngSoft.Evaluacion2.Variante.VarianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private MuebleService muebleService;

    @Autowired
    private VarianteService varianteService;

    @GetMapping
    public String mostrarCatalogo(Model model) {
        model.addAttribute("muebles", muebleService.obtenerTodosLosMuebles());
        model.addAttribute("variantes", varianteService.obtenerTodasLasVariantes());
        
        // Esta será la vista pública de compra
        return "cliente/catalogo"; 
    }
}