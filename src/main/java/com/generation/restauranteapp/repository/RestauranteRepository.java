package com.generation.restauranteapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.restauranteapp.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	public List<Restaurante> findAllByEnderecoContainingIgnoreCase(@Param("endereco") String endereco);

	public List<Restaurante> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	public Optional<Restaurante> findByNome(@Param("nome") String nome);

	public Optional<Restaurante> findByUsuario(String usuario);

}
