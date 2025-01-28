package com.generation.restauranteapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.restauranteapp.model.Restaurante;
import com.generation.restauranteapp.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	public ResponseEntity<List<Restaurante>> encontrarTodos() {
		return ResponseEntity.ok(restauranteRepository.findAll());

	}

	public ResponseEntity<Restaurante> encontrarPorId(Long id) {
		return restauranteRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public ResponseEntity<List<Restaurante>> encontrarPorEndereco(String endereco) {
		return ResponseEntity.ok(restauranteRepository.findAllByEnderecoContainingIgnoreCase(endereco));
	}
	
	public ResponseEntity<List<Restaurante>> encontrarPorNome(String nome) {
		return ResponseEntity.ok(restauranteRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	public ResponseEntity<Restaurante> criarRestaurante(Restaurante restaurante) {
		return ResponseEntity.status(HttpStatus.CREATED).body(restauranteRepository.save(restaurante));
	}

	public ResponseEntity<Restaurante> atualizarRestaurante(Restaurante restaurante) {
		return restauranteRepository.findById(restaurante.getId()).map(
				resposta -> ResponseEntity.status(HttpStatus.CREATED).body(restauranteRepository.save(restaurante)))

				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public void deletarRestaurante(Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);

		if (restaurante.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		restauranteRepository.deleteById(id);
	}

}
