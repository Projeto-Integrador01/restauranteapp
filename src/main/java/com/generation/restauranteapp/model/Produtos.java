package com.generation.restauranteapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O Atributo Nome é Obrigatório!")
	private String nome;

	@NotNull(message = "O atributo preço é obrigatório!")
	private Double preco;

	@Size(max = 5000, message = "O campo descrição é obrigatorio")
	private String descricao;

	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;

	@Size(max = 10, message = "O isSaudavel não poder ser maior que 10 caracteres")
	private String isSaudavel;

	@ManyToOne
	@JsonIgnoreProperties("produtos") 
	private List<Categoria> categoria;
	
	@ManyToOne
	@JsonIgnoreProperties("produtos") 
	private List<Restaurante> restaurante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getIsSaudavel() {
		return isSaudavel;
	}

	public void setIsSaudavel(String isSaudavel) {
		this.isSaudavel = isSaudavel;
	}

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}

	public List<Restaurante> getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(List<Restaurante> restaurante) {
		this.restaurante = restaurante;
	}
	
	
}
