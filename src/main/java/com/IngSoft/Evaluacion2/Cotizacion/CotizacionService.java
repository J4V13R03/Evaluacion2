package com.IngSoft.Evaluacion2.Cotizacion;

import com.IngSoft.Evaluacion2.Mueble.Mueble;
import com.IngSoft.Evaluacion2.Mueble.MuebleRepository;
import com.IngSoft.Evaluacion2.Variante.Variante;
import com.IngSoft.Evaluacion2.Variante.VarianteRepository;
import com.IngSoft.Evaluacion2.dto.CotizacionItemDTO;
import com.IngSoft.Evaluacion2.dto.CotizacionRequestDTO;
import com.IngSoft.Evaluacion2.Costos.Costos;
import com.IngSoft.Evaluacion2.Costos.PrecioBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private MuebleRepository muebleRepository;
    @Autowired
    private VarianteRepository varianteRepository;

    // Crear una nueva cotización 
    @Transactional
    public Cotizacion crearCotizacion(CotizacionRequestDTO request) {
        
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setFecha(LocalDateTime.now());
        cotizacion.setEstado("PENDIENTE");
        
        Integer totalGeneral = 0;

        for (CotizacionItemDTO itemDTO : request.items()) {
            
            // Encuentra el Mueble base
            Mueble muebleBase = muebleRepository.findById(itemDTO.muebleId())
                .orElseThrow(() -> new RuntimeException("Mueble no encontrado con ID: " + itemDTO.muebleId()));

            // Variantes seleccionadas
            Set<Variante> variantes = new HashSet<>(varianteRepository.findAllById(itemDTO.varianteIds()));
            
            Costos itemCotizable = new PrecioBuilder(muebleBase)
                                            .conVariantes(variantes)
                                            .build();

            // precio final del item (con variantes)
            Integer precioUnitario = itemCotizable.getPrecio();
            
            // se crea cotizacion item
            CotizacionItem item = new CotizacionItem();
            item.setMueble(muebleBase);
            item.setCantidad(itemDTO.cantidad());
            item.setVariantes(variantes);
            item.setPrecioItemCalculado(precioUnitario * itemDTO.cantidad());

            cotizacion.addItem(item);
            
            totalGeneral += item.getPrecioItemCalculado();
        }

        cotizacion.setTotalCalculado(totalGeneral);
        
        // Guardamos la cotizacion
        return cotizacionRepository.save(cotizacion);
    }
    
    // Confirmar la venta de una cotizacion
    @Transactional
    public Cotizacion confirmarVenta(Long cotizacionId) {
        // encuentra la cotizacion
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
            .orElseThrow(() -> new RuntimeException("Cotización no encontrada con ID: " + cotizacionId));

        if (!cotizacion.getEstado().equals("PENDIENTE")) {
            throw new RuntimeException("Esta cotización ya fue procesada o está en un estado inválido.");
        }

        // verifica stock suficiente
        for (CotizacionItem item : cotizacion.getItems()) {
            Mueble mueble = item.getMueble();
            if (mueble.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + mueble.getNombreMueble());
            }
        }

        // descuenta stock si hay suficiente
        for (CotizacionItem item : cotizacion.getItems()) {
            Mueble mueble = item.getMueble();
            int nuevoStock = mueble.getStock() - item.getCantidad();
            mueble.setStock(nuevoStock);
            muebleRepository.save(mueble); // actualizamos el stock del mueble en la BD
        }

        cotizacion.setEstado("VENDIDO");
        return cotizacionRepository.save(cotizacion);
    }
    
    public Optional<Cotizacion> obtenerCotizacionPorId(Long id) {
        return cotizacionRepository.findById(id);
    }
}