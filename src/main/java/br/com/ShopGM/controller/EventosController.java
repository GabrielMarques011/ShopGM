package br.com.ShopGM.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ShopGM.model.Eventos;
import br.com.ShopGM.repository.CategoriasRepository;
import br.com.ShopGM.repository.EventosRepository;

@Controller
public class EventosController {
	
	@Autowired
	private EventosRepository repositoryEvento;
	
	@Autowired
	private CategoriasRepository repository;

	@RequestMapping("formEventos")
	public String form(Model model) {
		//PARA BUSCAR A LISTA EM ORDEM ALFABETICA POR NOME
		model.addAttribute("categoria", repository.findAllByOrderByNomeCategoriaAsc());
		return "eventos";
	}
	
	@RequestMapping(value = "saveEvento", method = RequestMethod.POST)
	public String saveEvento(@Valid Eventos evento,@RequestParam("fileFotos")MultipartFile[] fileFotos) { //BindingResult result, RedirectAttributes attr
		/* repositoryEvento.save(evento); */
		System.out.println(fileFotos.length);//VENDO SE ELE ESTA RECEBENDO O VETOR QUE ESTA PASSANDO NO FORMULARIO
		return "redirect:formEventos";
	}
	
	
	
	
	
}