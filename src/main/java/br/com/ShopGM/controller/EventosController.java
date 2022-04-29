package br.com.ShopGM.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import br.com.ShopGM.model.Eventos;
import br.com.ShopGM.repository.CategoriasRepository;
import br.com.ShopGM.repository.EventosRepository;
import br.com.ShopGM.util.FireBaseUtil;

@Controller
public class EventosController {
	
	//INSTANCIANDO
	@Autowired
	private EventosRepository repositoryEvento;
	
	//INSTANCIANDO
	@Autowired
	private CategoriasRepository repository;
	
	//INSTANCIANDO
	@Autowired
	private FireBaseUtil fireUtil;

	@RequestMapping("formEventos")
	public String form(Model model) {
		//PARA BUSCAR A LISTA EM ORDEM ALFABETICA POR NOME
		model.addAttribute("categoria", repository.findAllByOrderByNomeCategoriaAsc());
		return "eventos";
	}
	
	@RequestMapping(value = "saveEvento", method = RequestMethod.POST)
	public String saveEvento(@Valid Eventos evento,@RequestParam("fileFotos")MultipartFile[] fileFotos) { //BindingResult result, RedirectAttributes attr
		
		//CRIANDO AS STRINGS PARA AS URLS
		String fotos = evento.getFotos();
		
		//PERCORRE CADA ARQUIVO NO VETOR
		for(MultipartFile arquivo : fileFotos) {
			//VERIFICANDO SE O ARQUIVO EXISTE
			//!!!O ARQUIVO NÃO VAI SER NULO (NULL)
			if (arquivo.getOriginalFilename().isEmpty()) {
				//PARA ELE SEGUIR PARA PROXIMA
				continue;
			}
			
			//FAZENDO O UPLOAD
			try {
				fotos += fireUtil.upload(arquivo)+";";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//ANTES DE SALVAR SALVA A VARIAVEL EVENTOS AS FOTOS
		evento.setFotos(fotos);
		//SALVANDO O EVENTO
		repositoryEvento.save(evento);
		/* System.out.println(fileFotos.length);*///VENDO SE ELE ESTA RECEBENDO O VETOR QUE ESTA PASSANDO NO FORMULARIO
		return "redirect:formEventos";
	}
	
	@RequestMapping("listaEvento/{page}")
	public String listaEvento(Model model, @PathVariable("page") int page) {

		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "id"));

		Page<Eventos> pagina = repositoryEvento.findAll(pageable);
		
		model.addAttribute("eventos", pagina.getContent());

		int totalpages = pagina.getTotalPages();

		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalpages; i++) {
			numPaginas.add(i);
		}
		
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalpages);
		model.addAttribute("pagAtual", page);
	
		return "listaEventos";
	}
	
	@RequestMapping("alterarEvento")
	public String alterarEvento(Long id, Model model) {
		Eventos evento = repositoryEvento.findById(id).get();
		model.addAttribute("eventos", evento);
		return "forward:formEventos";
	}
	
	@RequestMapping("excluirEvento")
	public String excluirEvento(Long id) {
		Eventos eventos = repositoryEvento.findById(id).get();
		if (eventos.getFotos().length() > 0) {
			for (String foto : eventos.verFotos()) {
				fireUtil.deletar(foto);
			}
		}
		repositoryEvento.delete(eventos);
		/* repositoryEvento.deleteById(id); */
		return "redirect:listaEvento/1";
	}
	
	@RequestMapping("excluirFoto")
	public String excluirFoto(Long idEventos, int numFoto, Model model) {
		//VOU PARA IR NO BACK E VOLTAR PARA O FORM
		//BUSCANDO O EVENTO
		Eventos eventos = repositoryEvento.findById(idEventos).get();
		//BUSCANDO A FOTO
		String urlFoto = eventos.verFotos()[numFoto];
		//DELETAR A FOTO
		fireUtil.deletar(urlFoto);
		//REMOVER A URL DO ATRIBUTO FOTOS
		eventos.setFotos(eventos.getFotos().replace(urlFoto+";", ""));
		//SALVA NO BD
		repositoryEvento.save(eventos);
		//COLOCA O EVENTO NA MODEL
		model.addAttribute("eventos", eventos);
		return "forward:formEventos";
	}
	
	
	
}