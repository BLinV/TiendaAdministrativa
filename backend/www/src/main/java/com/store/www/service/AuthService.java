package com.store.www.service;

import com.store.www.config.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.www.dto.LoginRequest;
import com.store.www.entity.Usuario;
import com.store.www.exception.CredencialesInvalidasException;
import com.store.www.repository.UsuarioRepositoryInterface;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioRepositoryInterface usuarioRepository;

    public AuthService(UsuarioRepositoryInterface usuarioRepository, BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsuario(request.usuario())
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));
        if (!passwordEncoder.matches(request.clave(), usuario.getClave()))
            throw new CredencialesInvalidasException("Credenciales inválidas");

        return jwtService.generarToken(usuario.getUsuario());
    }
}
