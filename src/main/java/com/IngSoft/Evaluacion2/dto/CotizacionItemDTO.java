package com.IngSoft.Evaluacion2.dto;

import java.util.List;

public record CotizacionItemDTO(
    Long muebleId,
    Integer cantidad,
    List<Long> varianteIds
) {
}