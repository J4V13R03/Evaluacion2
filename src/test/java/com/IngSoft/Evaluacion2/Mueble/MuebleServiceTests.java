package com.IngSoft.Evaluacion2.Mueble;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

//gestion de CRUD 
@ExtendWith(MockitoExtension.class)
public class MuebleServiceTests {

    @Mock
    private MuebleRepository muebleRepository;

    @InjectMocks
    private MuebleService muebleService;

    @Test
    public void testCrearMueble() {
        Mueble muebleAEnviar = new Mueble();
        muebleAEnviar.setNombreMueble("Silla de Prueba");
        
        Mueble muebleGuardado = new Mueble();
        muebleGuardado.setIdMueble(1L);
        muebleGuardado.setNombreMueble("Silla de Prueba");
        muebleGuardado.setEstado("activo");

        when(muebleRepository.save(any(Mueble.class))).thenReturn(muebleGuardado);

        Mueble resultado = muebleService.crearMueble(muebleAEnviar);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdMueble());
        assertEquals("activo", resultado.getEstado());
    }

    // Prueba que el metodo obtenerTodosLosMuebles devuelva una lista correcta
    @Test
    public void testObtenerTodosLosMuebles() {
        List<Mueble> listaFalsa = List.of(new Mueble(), new Mueble());
        when(muebleRepository.findAll()).thenReturn(listaFalsa);

        List<Mueble> resultado = muebleService.obtenerTodosLosMuebles();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    // Metodo desactivarMueble cambie el estadodel mueble a inactivo
    @Test
    public void testDesactivarMueble() {
        Mueble muebleExistente = new Mueble();
        muebleExistente.setIdMueble(1L);
        muebleExistente.setEstado("activo");

        when(muebleRepository.findById(1L)).thenReturn(Optional.of(muebleExistente));
        when(muebleRepository.save(muebleExistente)).thenReturn(muebleExistente);

        Mueble resultado = muebleService.desactivarMueble(1L);
        
        assertNotNull(resultado);
        assertEquals("inactivo", resultado.getEstado());
    }
}