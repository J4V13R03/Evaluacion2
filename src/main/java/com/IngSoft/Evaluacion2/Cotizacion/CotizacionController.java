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

    // Crear cotizacion
    @PostMapping
    public ResponseEntity<?> crearCotizacion(@RequestBody CotizacionRequestDTO request) {
        try {
            Cotizacion nuevaCotizacion = cotizacionService.crearCotizacion(request);
            return new ResponseEntity<>(nuevaCotizacion, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la cotización: " + e.getMessage());
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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}