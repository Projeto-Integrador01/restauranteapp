package com.generation.restauranteapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.restauranteapp.model.Restaurante;
import com.generation.restauranteapp.model.RestauranteLogin;
import com.generation.restauranteapp.repository.RestauranteRepository;
import com.generation.restauranteapp.security.JwtService;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

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

	public Optional<Restaurante> criarRestaurante(Restaurante restaurante) {
		if (restauranteRepository.findByUsuario(restaurante.getUsuario()).isPresent())
			return Optional.empty();

		restaurante.setSenha(criptografarSenha(restaurante.getSenha()));

		return Optional.of(restauranteRepository.save(restaurante));
	}

	public Optional<Restaurante> atualizarRestaurante(Restaurante restaurante) {
		if (restauranteRepository.findById(restaurante.getId()).isPresent()) {

			Optional<Restaurante> buscaUsuario = restauranteRepository.findByNome(restaurante.getUsuario());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != restaurante.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			restaurante.setSenha(criptografarSenha(restaurante.getSenha()));

			return Optional.ofNullable(restauranteRepository.save(restaurante));

		}

		return Optional.empty();

	}

	public void deletarRestaurante(Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);

		if (restaurante.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		restauranteRepository.deleteById(id);
	}

	public Optional<RestauranteLogin> autenticarRestaurante(Optional<RestauranteLogin> restauranteLogin) {

		var credenciais = new UsernamePasswordAuthenticationToken(restauranteLogin.get().getUsuario(), restauranteLogin.get().getSenha());

		Authentication authentication = authenticationManager.authenticate(credenciais);

		if (authentication.isAuthenticated()) {

			Optional<Restaurante> usuario = restauranteRepository.findByUsuario(restauranteLogin.get().getUsuario());

			if (usuario.isPresent()) {

				restauranteLogin.get().setId(usuario.get().getId());
				restauranteLogin.get().setNome(usuario.get().getNome());
				restauranteLogin.get().setFoto(usuario.get().getFoto());
				restauranteLogin.get().setToken(gerarToken(restauranteLogin.get().getUsuario()));
				restauranteLogin.get().setSenha("");

				return restauranteLogin;

			}

		}

		return Optional.empty();

	}

	private String gerarToken(String restaurante) {
		return "Bearer " + jwtService.generateToken(restaurante);
	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

}
