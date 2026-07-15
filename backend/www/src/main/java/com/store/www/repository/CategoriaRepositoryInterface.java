package com.store.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.www.entity.Categoria;

public interface CategoriaRepositoryInterface extends JpaRepository<Categoria, Long> { }