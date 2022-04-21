package br.com.ShopGM.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import br.com.ShopGM.annotation.Publico;
import br.com.ShopGM.model.AdminShop;
import br.com.ShopGM.repository.AdminRepository;
import br.com.ShopGM.util.HashUtil;

@Controller
public class AdminShopController {

	// vatiavel para persistencia dos dados
	@Autowired
	private AdminRepository repository;
	
	@RequestMapping("formAdmin")
	public String form() {
		return "index";
	}

	// @Valid vai validar o Admin
	// para analizar erros de capturação utilizo o BindingResult
	// para poder pensurar atributos RedirectAttributes
	@RequestMapping(value = "salvaAdmin", method = RequestMethod.POST)
	public String salvaAdmin(@Valid AdminShop admin, BindingResult result, RedirectAttributes attr) {

		// verifica se houveram erros de validação
		if (result.hasErrors()) {

			// add uma mensagem de erro
			attr.addFlashAttribute("mensagemErro", "Verifique os campos!");

			// redirecionando para o form
			return "redirect:formAdmin";
		}

		// variavel para descobrir alteração ou inserção
		boolean alteração = admin.getId() != null ? true : false;

		// verificando se a senha esta vazia
		if (admin.getSenha().equals(HashUtil.hash(""))) {// comparando com o hash, trazendo o hash para está parte como fazia

			if (!alteração) {

				// retirando a parte antes do @
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// setando a parte na senha do admin
				admin.setSenha(parte);

			} else {

				//buscando a senha atual do banco de dados
				String hash = repository.findById(admin.getId()).get().getSenha();//busquei a senha atual pelo hash
				//setar o hash na senha
				admin.setSenhaHash(hash);
				
			}

		}

		try {

			// salva no Banco a entidade
			repository.save(admin);

			// add uma mensagem
			// mostrando qual foi o id cadastrado na hora com o +admin.getId()
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso!! (caso não tenha colocado uma senha, será o restante do e-mail logo após o '@') ID:" + admin.getId());

		} catch (Exception e) {

			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());

		}

		return "redirect:formAdmin";

	}

	// criando o metodo pra retornar a lista
	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {// para criar a relação utilizei o
																			// @PathVariable

		// cria uma Pageable informando os paramentros da pagina
		// page menos um para comecar no lugar certi (por exemplo, 1 representa o 0)
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "id"));// os parametros para
																								// paginação

		// cria um page de administradores atraves dos parametros passados ao repository
		Page<AdminShop> pagina = repository.findAll(pageable);

		// add a model a lista com os admins
		model.addAttribute("admins", pagina.getContent());// colocando os admin pra page

		// pegando o total de paginas
		int totalpages = pagina.getTotalPages();

		// preenchendo o list com as paginas
		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalpages; i++) {
			// add paginas a list
			numPaginas.add(i);
		}

		// add valores na model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalpages);
		model.addAttribute("pagAtual", page);

		// retornando para a pagina de lista
		return "listaAdmin";
	}

	// metodo de excluir o admin
	@RequestMapping("excluiAdmin")
	public String excluiAdmin(Long id) {
		repository.deleteById(id);
		return "redirect:listaAdmin/1";
	}

	// metodo de editar o admin
	@RequestMapping("alterarAdmin")
	public String alterarAdmin(Long id, Model model) {
		AdminShop adminShop = repository.findById(id).get();
		model.addAttribute("adminShop", adminShop);
		return "forward:formAdmin";
	}
	
	@Publico
	//redirecionando depois da tela de login
	@RequestMapping("login")
	public String login(AdminShop adminLogin, RedirectAttributes attr, HttpSession session) {
		//BUSCANDO O ADMIN NO BANCO
		AdminShop admin = repository.findByEmailAndSenha(adminLogin.getEmail(), adminLogin.getSenha());
		if (admin == null) {
			attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido(s)");
			return "redirect:/";
		}else {
			//SALVANDO O ADMIN NA SESSÃO
			session.setAttribute("usuarioLogado", admin);
			//REDIRECIONANDO PARA A PROXIMA PAGINA
			return "redirect:/listaEvento/1";
		}
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		//INVALIDA A SESSÃO
		session.invalidate();
		//VOLTA PARA A PÁGINA INCIAL
		return "redirect:/";
	}

}