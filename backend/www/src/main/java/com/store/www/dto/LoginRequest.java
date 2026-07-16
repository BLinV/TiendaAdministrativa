package com.store.www.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
                @NotBlank
                @Size(max = 30)
                String usuario,
                
                @NotBlank
                @Size(min = 4, max = 100)
                String clave) {
}