package com.store.www.service;

import org.springframework.stereotype.Service;

import com.store.www.repository.CategoriaRepositoryInterface;
import com.store.www.repository.ProductoRepositoryInterface;
import com.store.www.dto.ProductoRequest;

import java.util.List;
import com.store.www.dto.ProductoResponse;
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
                p.getPrecio(), p.getStock(), p.getIdCategoria(),
                p.getFechaCreacion(), p.getFechaEdicion());
    }
    
    public List<ProductoResponse> listar() {
        return productoRepository.findAll()
                .stream()
                .map(producto -> new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getIdCategoria(),
                        producto.getFechaCreacion(),
                        producto.getFechaEdicion()))
                .toList();
    }

    public ProductoResponse crear(ProductoRequest request) {
        if (!categoriaRepository.existsById(request.idCategoria()))
            throw new RecursoNoEncontradoException("Categoría " + request.idCategoria() + " no encontrada");
        Producto producto = new Producto(request.nombre(), request.descripcion(), request.precio(), request.stock(),
                request.idCategoria());
        productoRepository.save(producto);
        return toResponse(producto);
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        if (!categoriaRepository.existsById(request.idCategoria()))
            throw new RecursoNoEncontradoException("Categoría " + request.idCategoria() + " no encontrada");
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setIdCategoria(request.idCategoria());
        productoRepository.save(producto);
        return toResponse(producto);
    }

    public ProductoResponse eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto " + id + " no encontrado"));
        productoRepository.delete(producto);
        return toResponse(producto);
    }
}
