package com.IngSoft.Evaluacion2.Cotizacion;

import com.IngSoft.Evaluacion2.dto.CotizacionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cotizaciones")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    // Crear una nueva cotización
    @PostMapping
    public ResponseEntity<Cotizacion> crearCotizacion(@RequestBody CotizacionRequestDTO request) {
        try {
            Cotizacion nuevaCotizacion = cotizacionService.crearCotizacion(request);
            return new ResponseEntity<>(nuevaCotizacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Ver una cotización por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cotizacion> obtenerCotizacion(@PathVariable Long id) {
        return cotizacionService.obtenerCotizacionPorId(id)
            .map(cotizacion -> new ResponseEntity<>(cotizacion, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Confirmar la venta de una cotización
    @PostMapping("/{id}/confirmar-venta")
    public ResponseEntity<?> confirmarVenta(@PathVariable Long id) {
        try {
            Cotizacion cotizacionVendida = cotizacionService.confirmarVenta(id);
            return new ResponseEntity<>(cotizacionVendida, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}