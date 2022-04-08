package br.com.ShopGM.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

//MAPEANDO
@Entity
@Data
public class Eventos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String endereco;
	private String bairro;
	private String estado;
	private String numero;
	private String complemento;
	private String telefone;
	private String horario;
	private String formaPagamento;
	private String posicao;
	private String idade;
	private int preco;
	private boolean acessibilidade;
	
	//RELAÇÃO ENTRE AS TABELAS
	@ManyToOne
	private Categorias categoria;
	
	//APLICANDO FOTO
	@Column(columnDefinition = "TEXT")
	private String fotos;
	
}