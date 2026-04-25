package com.novaluz.tienda.repository;

import com.novaluz.tienda.entity.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    List<Articulo> findByCategoriaId(Long categoriaId);
    List<Articulo> findByNombreContainingIgnoreCase(String nombre);
    List<Articulo> findByStockGreaterThan(Integer cantidad);
    boolean existsByNombreIgnoreCase(String nombre);
}
