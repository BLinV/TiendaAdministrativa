package com.store.www.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
                @NotBlank
                @Size(max = 100)
                String nombre,

                @NotBlank
                @Size(min = 4, max = 30)
                String usuario,

                @Size(min = 4, max = 100)
                String clave) {
}
