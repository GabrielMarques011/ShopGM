package br.com.ShopGM.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.ShopGM.annotation.Privado;
import br.com.ShopGM.annotation.Publico;
import br.com.ShopGM.model.Avaliacao;
import br.com.ShopGM.repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoRestController {
	
	@Autowired
	private AvaliacaoRepository repository;
	
	@Privado
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/api/avaliacao/"+avaliacao.getId())).body(avaliacao);
	}
	
	//se quisermos ver a avaliação pelo id
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Avaliacao getById (@PathVariable("id") Long idAvaliacao) {
		return repository.findById(idAvaliacao).get();
	}
	
	//listagem de todas as avaliações por evento
	@Publico
	@RequestMapping(value = "/eventos/{id}", method = RequestMethod.GET)
	public List<Avaliacao> getByEventos (@PathVariable("id") Long id) {
		return repository.findByEventosId(id);
	}
	
	
}