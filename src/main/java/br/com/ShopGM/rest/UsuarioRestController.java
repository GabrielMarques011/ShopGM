package br.com.ShopGM.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ShopGM.annotation.Privado;
import br.com.ShopGM.annotation.Publico;
import br.com.ShopGM.model.Erro;
import br.com.ShopGM.model.TokenJWT;
import br.com.ShopGM.model.Usuario;
import br.com.ShopGM.repository.UsuarioRepository;




@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	
	public static final String EMISSOR = "SENAI";
	//chave para acessar o EMISSOR
	public static final String SECRET = "G1A8B2R0IE0L6M2A0RQ0U3ES";

	@Autowired
	private UsuarioRepository repository;
	
	//criando metodo responsavel por devolver um usuario
	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		try {
			//insere no bd
			repository.save(usuario);
			//retorna um codigo 201, infroma como acessar o recurso inserido e acresenta no corpo o objeto inserido
			return ResponseEntity.created(URI.create("/api/usuario/"+usuario.getId())).body(usuario);	
		} catch (DataIntegrityViolationException e) {
			//para continuar mostrando o erro na tela
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Registro Duplicado", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> getEvento(@PathVariable("id") Long idUsuario){
		Optional<Usuario> optional = repository.findById(idUsuario);
		//se o evento existir
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
			
		}
		
	}
	
	
	
	
	@Privado
	//metodo para fazer o update dos usuarios
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuario,@PathVariable("id") Long id){
		
		//validacao do id
		if (id != usuario.getId()) {
			throw new RuntimeException("ID Inválido!!!");
		}
		
		repository.save(usuario);
		return ResponseEntity.ok().build();
	}
	
	
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		repository.deleteById(idUsuario);
		//codigo 204, onde devolve ele (204 indica que deu certo, e permanece na mesma pagina) 
		return ResponseEntity.noContent().build();
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenJWT> logar(@RequestBody Usuario usuario){
		
		//buscando o usuario no banco de dados
		usuario = repository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		
		//verifica se o usuario não é nulo (se existe ou não)
		if (usuario != null) {
			
			//variavel para inserir dados no payload
			Map<String, Object> payload = new HashMap<String, Object>();
			
			//inserindo os valores
			payload.put("idUser", usuario.getId());
			payload.put("nameUser", usuario.getNome());
			
			//token vai ter uma data de expiração
			Calendar expiracao = Calendar.getInstance();
			
			//adicionar valor no calendar
			expiracao.add(Calendar.HOUR, 5);
			
			//algoritmo para assinar o token
			Algorithm algoritmo = Algorithm.HMAC256(SECRET);
			
			//criando o Objeto para receber Token
			TokenJWT tokenJWT = new TokenJWT();
			
			//gerando o Token
			tokenJWT.setToken(JWT.create()
					.withPayload(payload)
					.withIssuer(EMISSOR)
					.withExpiresAt(expiracao.getTime())
					.sign(algoritmo));
			
			return ResponseEntity.ok(tokenJWT);
			
		}else {
			return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
