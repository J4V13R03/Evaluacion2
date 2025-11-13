package com.IngSoft.Evaluacion2.Cotizacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionItemRepository extends JpaRepository<CotizacionItem, Long> {
}