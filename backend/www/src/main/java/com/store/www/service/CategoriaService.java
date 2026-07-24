package com.store.www.service;

import org.springframework.stereotype.Service;
import com.store.www.repository.CategoriaRepositoryInterface;
import com.store.www.dto.CategoriaRequest;

import java.util.List;
import com.store.www.dto.CategoriaResponse;
import com.store.www.entity.Categoria;
import com.store.www.exception.RecursoNoEncontradoException;

@Service
public class CategoriaService {
    private final CategoriaRepositoryInterface categoriaRepository;

    public CategoriaService(CategoriaRepositoryInterface categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    private CategoriaResponse toResponse(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNombre(), c.getDescripcion(),
                c.getFechaCreacion(), c.getFechaEdicion());
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> toResponse(categoria))
                .toList();
    }

    public CategoriaResponse obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría " + id + " no encontrada"));
        return toResponse(categoria);
    }

    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria categoria = new Categoria(request.nombre(), request.descripcion());
        categoriaRepository.save(categoria);
        return toResponse(categoria);
    }

    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría " + id + " no encontrada"));

        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        categoriaRepository.save(categoria);
        return toResponse(categoria);
    }

    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría " + id + " no encontrada"));
        categoriaRepository.delete(categoria);
    }
}
