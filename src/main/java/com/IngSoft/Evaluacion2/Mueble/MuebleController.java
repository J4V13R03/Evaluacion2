package com.IngSoft.Evaluacion2.Mueble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muebles")
public class MuebleController {

    @Autowired
    private MuebleService muebleService;

    @PostMapping
    public ResponseEntity<Mueble> crearMueble(@RequestBody Mueble mueble) {
        Mueble nuevoMueble = muebleService.crearMueble(mueble);
        return new ResponseEntity<>(nuevoMueble, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Mueble>> listarMuebles() {
        List<Mueble> muebles = muebleService.obtenerTodosLosMuebles();
        return new ResponseEntity<>(muebles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mueble> obtenerMueblePorId(@PathVariable Long id) {
        return muebleService.obtenerMueblePorId(id)
                .map(mueble -> new ResponseEntity<>(mueble, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mueble> actualizarMueble(@PathVariable Long id, @RequestBody Mueble mueble) {
        Mueble muebleActualizado = muebleService.actualizarMueble(id, mueble);
        if (muebleActualizado != null) {
            return new ResponseEntity<>(muebleActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Mueble> desactivarMueble(@PathVariable Long id) {
        Mueble muebleDesactivado = muebleService.desactivarMueble(id);
        if (muebleDesactivado != null) {
            return new ResponseEntity<>(muebleDesactivado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}