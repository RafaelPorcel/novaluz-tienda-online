package com.novaluz.tienda.repository;

import com.novaluz.tienda.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByPadreId(Long padreId);
    List<Categoria> findByPadreIsNull();
    boolean existsByNombreIgnoreCase(String nombre);
}
