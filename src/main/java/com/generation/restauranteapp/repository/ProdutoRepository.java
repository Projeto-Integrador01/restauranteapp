package com.generation.restauranteapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.restauranteapp.model.Produtos;

public interface ProdutoRepository extends JpaRepository<Produtos, Long> {

	List<Produtos> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	
	List<Produtos> findAllByIsSaudavelContainingIgnoreCase(@Param("isSaudavel") String isSaudavel);

	

}
