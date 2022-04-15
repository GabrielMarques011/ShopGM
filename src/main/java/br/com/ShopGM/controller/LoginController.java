package br.com.ShopGM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ShopGM.repository.LoginRepository;

@Controller
public class LoginController {
	
	@Autowired
	private LoginRepository repositoryLogin;
	
	@RequestMapping("formLogin")
	public String form(/* Model model */) {
		/* model.addAttribute("login", repositoryLogin.findAll()) */
		return "telaLogin";
	}

}
