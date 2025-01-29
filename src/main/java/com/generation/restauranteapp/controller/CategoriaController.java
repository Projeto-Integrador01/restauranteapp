package com.generation.restauranteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.generation.restauranteapp.model.Categoria;
import com.generation.restauranteapp.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping // LISTAR TUDO
	public ResponseEntity<List<Categoria>> getAll() {
		return categoriaService.encontrarTodos();
	}

	@GetMapping("/{id}") // ENCONTRAR POR ID
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return categoriaService.encontarPorId(id);
	}

	@GetMapping("/nome/{nome}") // LISTAR POR NOME
	public ResponseEntity<List<Categoria>> getByNome(@PathVariable String nome) {
		return categoriaService.encontrarPorNome(nome);
	}

	@PutMapping // ATUALIZAR CATEGORIA
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) {
		return categoriaService.atualizar(categoria);
	}

	@PostMapping // CRIAR CATEGORIA
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) {
		return categoriaService.cadastrar(categoria);

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		categoriaService.deletar(id);
	}

}
