package com.store.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.www.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryInterface extends JpaRepository<Usuario, Long> { 
    Optional<Usuario> findByUsuario(String usuario);
}