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
import java.util.List;
import java.util.Set;

@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private MuebleRepository muebleRepository;
    @Autowired
    private VarianteRepository varianteRepository;

    @Transactional
    public Cotizacion crearCotizacion(CotizacionRequestDTO request) {
        
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setFecha(LocalDateTime.now());
        cotizacion.setEstado("PENDIENTE");
        
        Long totalGeneral = 0L;

        for (CotizacionItemDTO itemDTO : request.items()) {
            
            Mueble muebleBase = muebleRepository.findById(itemDTO.muebleId())
                .orElseThrow(() -> new RuntimeException("Mueble no encontrado ID: " + itemDTO.muebleId()));

            if (!"activo".equalsIgnoreCase(muebleBase.getEstado())) {
                throw new RuntimeException("El mueble '" + muebleBase.getNombreMueble() + "' no está disponible para la venta.");
            }

            List<Variante> variantesEncontradas = varianteRepository.findAllById(itemDTO.varianteIds());
            
            if (variantesEncontradas.size() != itemDTO.varianteIds().size()) {
                throw new RuntimeException("Una o más variantes seleccionadas no existen o no son válidas.");
            }
            
            Set<Variante> variantesSet = new HashSet<>(variantesEncontradas);

            // Decorator
            Costos itemCotizable = new PrecioBuilder(muebleBase)
                                            .conVariantes(variantesSet)
                                            .build();

            Long precioUnitario = itemCotizable.getPrecio();
            
            CotizacionItem item = new CotizacionItem();
            item.setMueble(muebleBase);
            item.setCantidad(itemDTO.cantidad());
            item.setVariantes(variantesSet);
            item.setPrecioItemCalculado(precioUnitario * itemDTO.cantidad());

            cotizacion.addItem(item);
            
            totalGeneral += item.getPrecioItemCalculado();
        }

        cotizacion.setTotalCalculado(totalGeneral);
        return cotizacionRepository.save(cotizacion);
    }
    
    @Transactional
    public Cotizacion confirmarVenta(Long cotizacionId) {
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
            .orElseThrow(() -> new RuntimeException("Cotización no encontrada ID: " + cotizacionId));

        if (!"PENDIENTE".equals(cotizacion.getEstado())) {
            throw new RuntimeException("Esta cotización ya no es válida.");
        }

        for (CotizacionItem item : cotizacion.getItems()) {
            Mueble mueble = item.getMueble();
            if (mueble.getStock() < item.getCantidad()) {
                throw new RuntimeException("Lo sentimos, el stock de '" + mueble.getNombreMueble() + "' se ha agotado.");
            }
        }

        // Descontar stock
        for (CotizacionItem item : cotizacion.getItems()) {
            Mueble mueble = item.getMueble();
            mueble.setStock(mueble.getStock() - item.getCantidad());
            muebleRepository.save(mueble);
        }

        cotizacion.setEstado("VENDIDO");
        return cotizacionRepository.save(cotizacion);
    }
    
    public java.util.Optional<Cotizacion> obtenerCotizacionPorId(Long id) {
        return cotizacionRepository.findById(id);
    }
}