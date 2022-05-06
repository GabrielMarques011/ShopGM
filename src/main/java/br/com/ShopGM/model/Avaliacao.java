package br.com.ShopGM.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Entity
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//irá aparecer so as avaliações com esse JsonProperty
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	private Eventos eventos;
	
	private double nota;
	private String comentario;
	
	@JsonFormat(pattern = "dd/MM/yyyy")//para formatar o jeito que a data sera exibida
	private Calendar dataAvaliacao;
	
	@ManyToOne
	private Usuario usuario;
	
}