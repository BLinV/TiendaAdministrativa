package com.store.www.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.www.entity.Usuario;
import com.store.www.repository.UsuarioRepositoryInterface;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepositoryInterface usuarioRepository;

    public UsuarioDetailsService(UsuarioRepositoryInterface usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getClave()) // HASH, tal cual está en la BD
                .authorities("USER") // rol genérico por ahora
                .build();
    }
}
