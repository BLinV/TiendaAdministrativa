package com.store.www.dto;

import java.math.BigDecimal;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductoRequest(
        @NotBlank @Size(max = 255) String nombre,
        @NotNull @Size(max = 255) String descripcion,
        @NotNull @Positive BigDecimal precio,
        @NotNull @PositiveOrZero Integer stock,
        @NotNull @ManyToOne Long idCategoria) {
}
