package com.store.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.www.entity.Usuario;

public interface UsuarioRepositoryInterface extends JpaRepository<Usuario, Long> { }