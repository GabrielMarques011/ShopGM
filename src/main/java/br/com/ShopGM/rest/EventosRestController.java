package br.com.ShopGM.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ShopGM.annotation.Publico;
import br.com.ShopGM.model.Eventos;
import br.com.ShopGM.repository.EventosRepository;

//RESPONDER REQUISIÇÕES REST
@RestController
@RequestMapping("/api/eventos")
public class EventosRestController {

	@Autowired
	private EventosRepository repositoryEventos;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Eventos> getEventos(){
		//LISTA TODOS
		return repositoryEventos.findAll();
	}
	
	@Publico
	//METODO QUE BUSCAR O EVENTO PELO ID
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Eventos> getEventos(@PathVariable("id") Long id){
		//TENTANDO BUSCAR O EVENTO, NO REPOSITORY
		Optional<Eventos> optional = repositoryEventos.findById(id);
		//SE O EVENTO EXISTIR
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Publico
	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.GET)
	public List<Eventos> getEvento(@PathVariable("id")Long id){
		return repositoryEventos.findByCategoriaIdCategoria(id);
	}
	
}