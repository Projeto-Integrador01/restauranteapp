package com.generation.restauranteapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.restauranteapp.model.Produto;
import com.generation.restauranteapp.repository.CategoriaRepository;
import com.generation.restauranteapp.repository.ProdutoRepository;

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

	public ResponseEntity<List<Produto>> procurarPorIsSaudavel(String isSaudavel) {
		if (isSaudavel.equalsIgnoreCase("verdadeiro")) {
			List<Produto> produtos = produtoRepository.findAllByIsSaudavelContainingIgnoreCase("verdadeiro");
			return ResponseEntity.ok(produtos);
		} else if (isSaudavel.equalsIgnoreCase("falso")) {
			List<Produto> produtos = produtoRepository.findAllByIsSaudavelContainingIgnoreCase("falso");
			return ResponseEntity.ok(produtos);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Digite verdadeiro ou falso para exibir os alimentos");

	}

	public ResponseEntity<Produto> atualizarProduto(Produto produto) {
		return produtoRepository.findById(produto.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public ResponseEntity<Produto> criarProduto(Produto produto) {
		if (categoriaRepository.findById(produto.getCategoria().getId()).isPresent()) {
			return ResponseEntity.ok(produtoRepository.save(produto));
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não existente");
	}

	public void deletarProduto(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		produtoRepository.deleteById(id);
	}
}
