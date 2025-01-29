package com.generation.restauranteapp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import com.generation.restauranteapp.model.Restaurante;
import com.generation.restauranteapp.repository.RestauranteRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<Restaurante> restaurante = restauranteRepository.findByUsuario(userName);

		if (restaurante.isPresent())
			return new UserDetailsImpl(restaurante.get());    
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
	}
}
