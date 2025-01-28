package com.generation.restauranteapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.restauranteapp.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	List<Restaurante> findAllByEnderecoContainingIgnoreCase(@Param("endereco") String endereco);

}
