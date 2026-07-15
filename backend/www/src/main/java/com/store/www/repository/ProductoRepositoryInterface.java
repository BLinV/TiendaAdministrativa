package com.store.www.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.store.www.entity.Producto;

import java.util.List;

public interface ProductoRepositoryInterface extends JpaRepository<Producto, Long> {
    @Query("""
            SELECT p FROM Producto p WHERE
              (:idCategoria IS NULL OR p.idCategoria = :idCategoria) AND
              (:precioMax   IS NULL OR p.precio <= :precioMax)       AND
              (:nombre      IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
            """)
    List<Producto> buscar(@Param("idCategoria") Long idCategoria,
            @Param("precioMax") BigDecimal precioMax,
            @Param("nombre") String nombre);
}
