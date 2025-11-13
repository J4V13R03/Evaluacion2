package com.IngSoft.Evaluacion2.dto;

import java.util.List;

public record CotizacionRequestDTO(
    List<CotizacionItemDTO> items
) {
}