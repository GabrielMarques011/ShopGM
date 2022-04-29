package br.com.ShopGM.model;



import org.springframework.http.HttpStatus;

import lombok.Data;

//para geral o get e set
@Data
public class Erro {

	private HttpStatus statusCode;
	private String mensagem;
	private String exception;
	
	public Erro(HttpStatus status, String msg, String exc){
		this.statusCode = status;
		this.exception = exc;
		this.mensagem = msg;
	}
	
}
