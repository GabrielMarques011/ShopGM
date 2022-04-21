package br.com.ShopGM.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ShopGM.annotation.Publico;
import br.com.ShopGM.repository.LoginRepository;

@Controller
public class LoginController {
	
	@Autowired
	private LoginRepository repositoryLogin;
	
	@Publico
	@RequestMapping("formLogin")
	public String form(HttpSession session) {
		
		session.invalidate();
		
		return "telaLogin";
	}

}
