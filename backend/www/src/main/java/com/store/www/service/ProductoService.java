package com.store.www.service;

import org.springframework.stereotype.Service;

import com.store.www.repository.CategoriaRepositoryInterface;
import com.store.www.repository.ProductoRepositoryInterface;
import com.store.www.dto.ProductoRequest;

import java.math.BigDecimal;
import java.util.List;
import com.store.www.dto.ProductoResponse;
import com.store.www.entity.Categoria;
import com.store.www.entity.Producto;
import com.store.www.exception.RecursoNoEncontradoException;

@Service
public class ProductoService {
    private final ProductoRepositoryInterface productoRepository;
    private final CategoriaRepositoryInterface categoriaRepository;

    public ProductoService(ProductoRepositoryInterface productoRepository,
            CategoriaRepositoryInterface categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    private ProductoResponse toResponse(Producto p) {
        return new ProductoResponse(p.getId(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getStock(), p.getCategoria().getId(),
                p.getCategoria().getNombre(), p.getFechaCreacion(), p.getFechaEdicion());
    }

    public List<ProductoResponse> listar(Long id, BigDecimal precioMax, String nombre) {
        return productoRepository.buscar(id, precioMax, nombre)
                .stream()
                .map(producto -> toResponse(producto))
                .toList();
    }

    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = productoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
        return toResponse(producto);
    }

    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.idCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría " + request.idCategoria() + " no encontrada"));
        Producto producto = new Producto(request.nombre(), request.descripcion(), request.precio(), request.stock(), categoria);
        productoRepository.save(producto);
        return toResponse(producto);
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.idCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría " + request.idCategoria() + " no encontrada"));
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setCategoria(categoria);
        productoRepository.save(producto);
        return toResponse(producto);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
        productoRepository.delete(producto);
    }
}
