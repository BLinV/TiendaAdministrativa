package com.store.www.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.store.www.exception.RecursoNoEncontradoException;
import com.store.www.repository.CategoriaRepositoryInterface;
import com.store.www.repository.ProductoRepositoryInterface;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.store.www.dto.ProductoRequest;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock ProductoRepositoryInterface productoRepository;
    @Mock CategoriaRepositoryInterface categoriaRepository;
    @InjectMocks ProductoService productoService;

    @Test
    void crear_categoriaInexistente_lanzaExcepcion() {
        // ARRANGE: la categoría 999 no existe
        when(categoriaRepository.existsById(999L)).thenReturn(false);
        var request = new ProductoRequest("Test", "desc", new BigDecimal("10"), 5, 999L);

        // ACT + ASSERT
        assertThrows(RecursoNoEncontradoException.class,
                () -> productoService.crear(request));
    }
    
}
