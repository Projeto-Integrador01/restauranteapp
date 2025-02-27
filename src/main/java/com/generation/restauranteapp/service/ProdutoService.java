package com.generation.restauranteapp.service;

import com.generation.restauranteapp.model.Produto;
import com.generation.restauranteapp.repository.CategoriaRepository;
import com.generation.restauranteapp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ResponseEntity<List<Produto>> encontrarTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());

	}

	public ResponseEntity<Produto> procurarPorId(Long id) {
		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public ResponseEntity<List<Produto>> procurarPorNome(String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	public ResponseEntity<List<Produto>> procurarPorTipoAlimento(String tipoAlimento) {
		List<String>tiposValidos = List.of("vegetariano", "vegano", "tradicional");
		if (!tiposValidos.contains(tipoAlimento.toLowerCase())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
		}
		List<Produto> produtos = produtoRepository.findAllByTipoAlimentoContainingIgnoreCase(tipoAlimento);
		return ResponseEntity.ok(produtos);
		}

	public ResponseEntity<Produto> atualizarProduto(Produto produto) {
		return produtoRepository.findById(produto.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public ResponseEntity<Produto> criarProduto(Produto produto) {
		List<String>tiposValidos = List.of("vegetariano", "vegano", "tradicional");

		if (!tiposValidos.contains(produto.getTipoAlimento().toLowerCase())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de alimento inválido! Escolha entre: vegetariano, vegano ou tradicional.");
		}
		if (categoriaRepository.findById(produto.getCategoria().getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não existente");
		}
		return ResponseEntity.ok(produtoRepository.save(produto));

	}

	public void deletarProduto(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		produtoRepository.deleteById(id);
	}
}
