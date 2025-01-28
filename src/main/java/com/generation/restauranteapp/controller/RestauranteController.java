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

import com.generation.restauranteapp.model.Restaurante;
import com.generation.restauranteapp.service.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping // LISTAR TUDO
	public ResponseEntity<List<Restaurante>> getAll() {
		return restauranteService.encontrarTodos();
	}

	@GetMapping("/{id}") // ENCONTRAR POR ID
	public ResponseEntity<Restaurante> getById(@PathVariable Long id) {
		return restauranteService.encontrarPorId(id);
	}

	@GetMapping("/endereco/{endereco}")
	public ResponseEntity<List<Restaurante>> getByEndereco(@PathVariable String endereco) {
		return restauranteService.encontrarPorEndereco(endereco);
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Restaurante>> findAllByNome(@PathVariable String nome){
		return  restauranteService.encontrarPorNome(nome);
	}

	@PutMapping // ATUALIZAR CATEGORIA
	public ResponseEntity<Restaurante> put(@Valid @RequestBody Restaurante restaurante) {
		return restauranteService.atualizarRestaurante(restaurante);
	}

	@PostMapping // CRIAR CATEGORIA
	public ResponseEntity<Restaurante> post(@Valid @RequestBody Restaurante restaurante) {
		return restauranteService.criarRestaurante(restaurante);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		restauranteService.deletarRestaurante(id);
	}
}
