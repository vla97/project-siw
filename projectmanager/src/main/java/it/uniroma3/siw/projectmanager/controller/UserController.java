package it.uniroma3.siw.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.UserService;

public class UserController {
	//private UserValidator userValidator;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/Inseriscidati", method = RequestMethod.GET)
	public String inserisciDati(Model model) {
		model.addAttribute("user", new User());	
		return "userForm.html";
	}

}
