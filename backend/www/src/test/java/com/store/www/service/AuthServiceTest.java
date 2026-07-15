package com.store.www.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.store.www.config.JwtService;
import com.store.www.exception.CredencialesInvalidasException;
import com.store.www.repository.UsuarioRepositoryInterface;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.store.www.dto.LoginRequest;
import com.store.www.entity.Usuario;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UsuarioRepositoryInterface usuarioRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @Test
    void autenticar_usuarioNoExiste_lanzaExcepcion() {
        // ARRANGE: prepara
        when(usuarioRepository.findByUsuario("fantasma")).thenReturn(Optional.empty());
        var request = new LoginRequest("fantasma", "loquesea");

        // ACT + ASSERT
        assertThrows(CredencialesInvalidasException.class,
                () -> authService.autenticar(request));
    }

    @Test
    void autenticar_usuarioClaveMala_lanzaExcepcion() {
        Usuario usuario = new Usuario("Admin", "admin", "$2b$10$hashGuardado");
        when(usuarioRepository.findByUsuario("admin")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("admin421", "$2b$10$hashGuardado")).thenReturn(false);
        var request = new LoginRequest("admin", "admin421");

        assertThrows(CredencialesInvalidasException.class,
                () -> authService.autenticar(request));
    }

    @Test
    void autenticar_usuarioClaveCorrecta_lanzaValidacion() {
        Usuario usuario = new Usuario("Admin", "admin", "$2b$10$hashGuardado");
        when(usuarioRepository.findByUsuario("admin")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("admin123", "$2b$10$hashGuardado")).thenReturn(true); // ← clave OK
        when(jwtService.generarToken("admin")).thenReturn("token-falso");

        String resultado = authService.autenticar(new LoginRequest("admin", "admin123"));

        assertEquals("token-falso", resultado); // ← verificas que DEVUELVE el token, no que lance

    }
}