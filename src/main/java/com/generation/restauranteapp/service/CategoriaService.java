package com.generation.restauranteapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.restauranteapp.model.Categoria;
import com.generation.restauranteapp.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ResponseEntity<List<Categoria>> encontrarTodos() {
		return ResponseEntity.ok(categoriaRepository.findAll());

	}

	public ResponseEntity<Categoria> encontarPorId(Long id) {
		return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public ResponseEntity<List<Categoria>> encontrarPorNome(String nome) {
		return ResponseEntity.ok(categoriaRepository.findCategoriaByNomeContainingIgnoreCase(nome));
	}

	public ResponseEntity<Categoria> atualizar(Categoria categoria) {
		if (categoriaRepository.findById(categoria.getId()).isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não existe");
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
	}

	public ResponseEntity<Categoria> cadastrar(Categoria categoria) {
		if (categoriaRepository.findCategoriaByNomeContainingIgnoreCase(categoria.getNome()).isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Nome de categoria já existente: " + categoria.getNome());
		}
	}

	public void deletar(Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);

		if (categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		categoriaRepository.deleteById(id);

	}
}