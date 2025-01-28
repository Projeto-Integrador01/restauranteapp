package com.generation.restauranteapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.restauranteapp.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    public List<Categoria> findCategoriaByNomeContainingIgnoreCase(@Param("nome")String nome);

}