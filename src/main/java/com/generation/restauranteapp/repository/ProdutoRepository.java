package com.generation.restauranteapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.restauranteapp.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	
	List<Produto> findAllByIsSaudavelContainingIgnoreCase(@Param("isSaudavel") String isSaudavel);

	

}
