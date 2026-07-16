package com.store.www.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponse(
                Long id,
                String nombre,
                String descripcion,
                BigDecimal precio,
                Integer stock,
                Long idCategoria,
                LocalDateTime fechaCreacion,
                LocalDateTime fechaEdicion) {
}
