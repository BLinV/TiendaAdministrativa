package com.store.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import com.store.www.dto.ProductoRequest;
import com.store.www.dto.ProductoResponse;
import com.store.www.service.ProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponse> listar(
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) String nombre) {
        return productoService.listar(idCategoria, precioMax, nombre);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponse> eliminar(@PathVariable Long id) {
        ProductoResponse response = productoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
