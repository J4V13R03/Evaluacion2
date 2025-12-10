package com.IngSoft.Evaluacion2.web;

import com.IngSoft.Evaluacion2.Mueble.Mueble;
import com.IngSoft.Evaluacion2.Mueble.MuebleService;
import com.IngSoft.Evaluacion2.Variante.Variante;
import com.IngSoft.Evaluacion2.Variante.VarianteService;
import com.IngSoft.Evaluacion2.Cotizacion.CotizacionRepository;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MuebleService muebleService;

    @Autowired
    private VarianteService varianteService;

    @Autowired
    private CotizacionRepository cotizacionRepository; 

    // Dashboard
    @GetMapping
    public String dashboard() {
        return "admin/dashboard";
    }

    // gestión de muebles

    @GetMapping("/muebles")
    public String listarMuebles(Model model) {
        model.addAttribute("muebles", muebleService.obtenerTodosLosMuebles());
        return "admin/muebles/muebles-list";
    }

    @GetMapping("/muebles/nuevo")
    public String nuevoMueble(Model model) {
        model.addAttribute("mueble", new Mueble());
        return "admin/muebles/muebles-form";
    }

    @GetMapping("/muebles/editar/{id}")
    public String editarMueble(@PathVariable Long id, Model model) {
        Mueble mueble = muebleService.obtenerMueblePorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("mueble", mueble);
        return "admin/muebles/muebles-form";
    }

    @GetMapping("/muebles/activar/{id}")
    public String activarMueble(@PathVariable Long id) {
        muebleService.activarMueble(id);
        return "redirect:/admin/muebles";
    }

    @PostMapping("/muebles/guardar")
    public String guardarMueble(@ModelAttribute Mueble mueble, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si falla, volvemos al formulario para mostrar el error
            return "admin/muebles/muebles-form"; 
        }

        if (mueble.getIdMueble() != null) {
            muebleService.actualizarMueble(mueble.getIdMueble(), mueble);
        } else {
            muebleService.crearMueble(mueble);
        }
        return "redirect:/admin/muebles";
    }

    @GetMapping("/muebles/desactivar/{id}")
    public String desactivarMueble(@PathVariable Long id) {
        muebleService.desactivarMueble(id);
        return "redirect:/admin/muebles";
    }

    // --- GESTIÓN DE VARIANTES ---

    @GetMapping("/variantes")
    public String listarVariantes(Model model) {
        model.addAttribute("variantes", varianteService.obtenerTodasLasVariantes());
        return "admin/variantes/variantes-list";
    }

    @GetMapping("/variantes/nuevo")
    public String nuevaVariante(Model model) {
        model.addAttribute("variante", new Variante());
        return "admin/variantes/variantes-form";
    }

    @GetMapping("/variantes/editar/{id}")
    public String editarVariante(@PathVariable Long id, Model model) {
        Variante variante = varianteService.obtenerVariantePorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("variante", variante);
        return "admin/variantes/variantes-form";
    }

    @PostMapping("/variantes/guardar")
    public String guardarVariante(@ModelAttribute Variante variante, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/variantes/variantes-form";
        }

        varianteService.crearVariante(variante);
        return "redirect:/admin/variantes";
    }

    @GetMapping("/variantes/eliminar/{id}")
    public String eliminarVariante(@PathVariable Long id) {
        varianteService.eliminarVariante(id);
        return "redirect:/admin/variantes";
    }

    // --- HISTORIAL DE VENTAS ---

    @GetMapping("/ventas")
    public String moduloVentas(Model model) {
        model.addAttribute("ventas", cotizacionRepository.findAll());
        return "admin/ventas";
    }
}