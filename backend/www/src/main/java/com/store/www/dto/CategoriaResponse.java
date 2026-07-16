package com.store.www.dto;

import java.time.LocalDateTime;

public record CategoriaResponse(
                Long id,
                String nombre,
                String descripcion,
                LocalDateTime fechaCreacion,
                LocalDateTime fechaEdicion) {
}
