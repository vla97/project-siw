package it.uniroma3.siw.projectmanager.controller;


import java.util.List;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import it.uniroma3.siw.projectmanager.model.Credenziali;

import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;



@Controller
public class UserController {
	// private UserValidator userValidator;


	@Autowired
	private CredenzialiService credenzialiService;



	@Autowired
	SessionData sessionData;

	@RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
	public String me(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		Credenziali credenziali = sessionData.getLoggedCredenziali();
		System.out.println(credenziali.getPassword());
		model.addAttribute("user", loggedUser);
		model.addAttribute("credenziali", credenziali);

		return "user";
	}

	
	@RequestMapping(value = { "/admin/users" }, method = RequestMethod.GET)
	public String listaUtenti(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		List<Credenziali> tuttiCredenziali = this.credenzialiService.getTuttiCredenziali();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("listaCredenziali", tuttiCredenziali);
		return "tuttiUtenti";
	}

	@RequestMapping(value = { "/admin/users/delete" }, method = RequestMethod.POST)
	public String cancellaUtente(Model model, @ModelAttribute("id") Long id) {

		this.credenzialiService.cancellaCredenziali(id);
		return "redirect:/admin/users";
	}

}