package com.store.www.service;

import org.springframework.stereotype.Service;
import com.store.www.repository.CategoriaRepositoryInterface;

import java.util.List;
import com.store.www.dto.CategoriaResponse;

@Service
public class CategoriaService {
    private final CategoriaRepositoryInterface categoriaRepository;

    public CategoriaService(CategoriaRepositoryInterface categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> new CategoriaResponse(
                        categoria.getId(),
                        categoria.getNombre(),
                        categoria.getDescripcion(),
                        categoria.getFechaCreacion(),
                        categoria.getFechaEdicion()))
                .toList();
    }
}
