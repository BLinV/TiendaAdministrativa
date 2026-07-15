package com.store.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.www.entity.Producto;

public interface ProductoRepositoryInterface extends JpaRepository<Producto, Long> { }