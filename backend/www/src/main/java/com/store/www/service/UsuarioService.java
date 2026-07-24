package com.store.www.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.www.dto.UsuarioRequest;
import com.store.www.dto.UsuarioResponse;
import com.store.www.entity.Usuario;
import com.store.www.exception.RecursoNoEncontradoException;
import com.store.www.repository.UsuarioRepositoryInterface;

@Service
public class UsuarioService {
    private final UsuarioRepositoryInterface usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepositoryInterface usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getUsuario(), u.getFechaCreacion(),
                u.getFechaEdicion());
    }

    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> toResponse(usuario))
                .toList();
    }

    public UsuarioResponse obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario " + id + " no encontrado"));
        return toResponse(usuario);
    }

    public UsuarioResponse crear(UsuarioRequest request) {
        if (request.clave() == null || request.clave().isBlank())
            throw new IllegalArgumentException("La clave es obligatoria al crear un usuario");
        String hash = passwordEncoder.encode(request.clave());
        Usuario usuario = new Usuario(request.nombre(), request.usuario(), hash);
        usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario " + id + " no encontrado"));

        usuario.setNombre(request.nombre());
        usuario.setUsuario(request.usuario());
        if (request.clave() != null && !request.clave().isBlank()) {
            usuario.setClave(passwordEncoder.encode(request.clave())); // ← ¡HASHEAR otra vez!
        }
        usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario " + id + " no encontrado"));
        usuarioRepository.delete(usuario);
    }

}
