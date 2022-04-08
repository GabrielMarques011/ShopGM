package br.com.ShopGM.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ShopGM.model.AdminShop;
import br.com.ShopGM.model.Categorias;
import br.com.ShopGM.repository.CategoriasRepository;
import br.com.ShopGM.util.HashUtil;

@Controller
public class CategoriasShopController {

	// chamando o repository do Ingresso
	@Autowired
	public CategoriasRepository repository;

	// direcionando o html do formulario do ingresso
	@RequestMapping("formCategoria")
	public String form() {
		return "indexCategorias";
	}

	@RequestMapping(value = "salvarCategoria", method = RequestMethod.POST)
	public String salvarCategoria(@Valid Categorias categoria, BindingResult result, RedirectAttributes attr) {
		repository.save(categoria);
		return "redirect:formCategoria";
	}

	@RequestMapping("listaCategoria/{page}")
	public String listaCategoria(Model model, @PathVariable("page") int page) {

		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "idCategoria"));

		Page<Categorias> pagina = repository.findAll(pageable);
		
		model.addAttribute("categoria", pagina.getContent());

		int totalpages = pagina.getTotalPages();

		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalpages; i++) {
			numPaginas.add(i);
		}
		
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalpages);
		model.addAttribute("pagAtual", page);
	
		return "listaCategorias";
	}

	@RequestMapping("excluirCategoria")
	public String excluirCategoria(Long idCategoria) {
		repository.deleteById(idCategoria);
		return "redirect:listaCategoria/1";
	}

	@RequestMapping("alterarCategoria")
	public String alterarCategoria(Long idCategoria, Model model) {
		Categorias categoria = repository.findById(idCategoria).get();
		model.addAttribute("categoria", categoria);// o nome da do "th:value" irá ser o mesmo deste, para que ele consiga trazer os inputs
		return "forward:formCategoria";// forward significa encaminhar
	}
	
	//BUSCANDO A PALAVRA CHAVE UTILIZANDO O REPOSITORY
	@RequestMapping("buscarPalavrasChave")
	public String buscarPalavrasChave(String palavraChave, Model model) {
		model.addAttribute("categoria", repository.buscarPalavrasChave(palavraChave));
		return "listaCategorias";
	}

}