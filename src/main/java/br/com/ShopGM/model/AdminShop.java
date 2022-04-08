package br.com.ShopGM.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.com.ShopGM.util.HashUtil;
import lombok.Data;

//cria os get e set
@Data
//mapeia a entidade para o JPA 
@Entity
public class AdminShop {

	// chave primaria e auto-icremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// para que o nome não seja vazio
	@NotEmpty
	private String nome;

	// define a coluna email com o um índice único
	@Column(unique = true)
	@Email
	private String email;

	// para que a senha não fique fazia
	@NotEmpty
	private String senha;

	// @Length()
	// @CPF
	// @CNPJ
	// @EAN
	// @NotNull 

	//metodo set que aplica hash(transforma a senha em caracters) na senha
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	
	//metodo que seta a senha retorne completa
	public void setSenhaHash(String hash) {
		this.senha = HashUtil.hash(hash);
	}
	
}