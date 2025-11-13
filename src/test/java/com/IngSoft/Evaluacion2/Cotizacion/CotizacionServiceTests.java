package com.IngSoft.Evaluacion2.Cotizacion;

import com.IngSoft.Evaluacion2.Mueble.Mueble;
import com.IngSoft.Evaluacion2.Mueble.MuebleRepository;
import com.IngSoft.Evaluacion2.Variante.Variante;
import com.IngSoft.Evaluacion2.Variante.VarianteRepository;
import com.IngSoft.Evaluacion2.dto.CotizacionItemDTO;
import com.IngSoft.Evaluacion2.dto.CotizacionRequestDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CotizacionServiceTests {

    @Mock
    private CotizacionRepository cotizacionRepository;
    @Mock
    private MuebleRepository muebleRepository;
    @Mock
    private VarianteRepository varianteRepository;

    @InjectMocks
    private CotizacionService cotizacionService;

    // verifica que el precio de una cotizacion sea la suma del precio base del mueble mas el costo de sus variantes
    @Test
    public void testCrearCotizacion_CalculaPrecioConVariantes() {
        Mueble muebleBase = new Mueble();
        muebleBase.setIdMueble(1L);
        muebleBase.setPrecioBase(100000); 

        Variante v1 = new Variante();
        v1.setIdVariante(1L);
        v1.setCostoAdicional(20000); 

        Variante v2 = new Variante();
        v2.setIdVariante(2L);
        v2.setCostoAdicional(30000);

        CotizacionItemDTO itemDTO = new CotizacionItemDTO(1L, 1, List.of(1L, 2L));
        CotizacionRequestDTO requestDTO = new CotizacionRequestDTO(List.of(itemDTO));

        when(muebleRepository.findById(1L)).thenReturn(Optional.of(muebleBase));
        when(varianteRepository.findAllById(any())).thenReturn(List.of(v1, v2));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cotizacion resultado = cotizacionService.crearCotizacion(requestDTO);

        assertNotNull(resultado);
        assertEquals(150000, resultado.getTotalCalculado());
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals(1, resultado.getItems().size());
        assertEquals(150000, resultado.getItems().get(0).getPrecioItemCalculado());
    }

    // cuando se confirme una venta con stock suficiente, el stock se descuente correctamente y el estado de la cotizacion cambie a "VENDIDO" 
    @Test
    public void testConfirmarVenta_StockSuficiente() {
        Mueble muebleEnStock = new Mueble();
        muebleEnStock.setNombreMueble("Silla");
        muebleEnStock.setStock(10); 

        CotizacionItem item = new CotizacionItem();
        item.setMueble(muebleEnStock);
        item.setCantidad(2); 

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setEstado("PENDIENTE");
        cotizacion.addItem(item);

        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);

        Cotizacion resultado = cotizacionService.confirmarVenta(1L);

        assertNotNull(resultado);
        assertEquals("VENDIDO", resultado.getEstado());
        assertEquals(8, muebleEnStock.getStock());
        verify(muebleRepository, times(1)).save(muebleEnStock);
    }

    // al confirmar una venta sin stock suficiente,el sistema lance una excepcion y no modifique el stock y el estado de la cotizacion 
    @Test
    public void testConfirmarVenta_StockInsuficiente_LanzaError() {
        Mueble muebleSinStock = new Mueble();
        muebleSinStock.setNombreMueble("Mesa de Centro");
        muebleSinStock.setStock(1);

        CotizacionItem item = new CotizacionItem();
        item.setMueble(muebleSinStock);
        item.setCantidad(5);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setEstado("PENDIENTE");
        cotizacion.addItem(item);

        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cotizacionService.confirmarVenta(1L);
        });
        
        assertEquals("Stock insuficiente para: Mesa de Centro", exception.getMessage());
        assertEquals("PENDIENTE", cotizacion.getEstado());
        assertEquals(1, muebleSinStock.getStock());
        verify(muebleRepository, never()).save(any(Mueble.class));
    }
}