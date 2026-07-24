package com.store.www.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String mensaje, Map<String, String> campos) {
    public ErrorResponse(String mensaje) {
        this(mensaje, null);
    }
}
