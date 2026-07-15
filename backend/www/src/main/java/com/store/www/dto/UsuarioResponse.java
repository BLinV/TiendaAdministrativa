package com.store.www.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nombre,
        String usuario,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaEdicion) {
}
