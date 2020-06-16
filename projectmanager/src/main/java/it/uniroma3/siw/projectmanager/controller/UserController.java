package it.uniroma3.siw.projectmanager.controller;

import java.util.List;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.projectmanager.model.Credenziali;

import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;
import it.uniroma3.siw.projectmanager.service.UserService;

@Controller
public class UserController {

	@Autowired
	private CredenzialiService credenzialiService;
	@Autowired
	private UserService userService;

	@Autowired
	SessionData sessionData;

	@GetMapping(value = { "/users/me" })
	public String me(Model model) {

		User loggedUser = sessionData.getLoggedUser();
		Credenziali credenziali = sessionData.getLoggedCredenziali();
		System.out.println(credenziali.getPassword());
		model.addAttribute("user", loggedUser);
		model.addAttribute("credenziali", credenziali);
		return "user";
	}

	@GetMapping(value = "/aggiornaUtente")
	public String aggiornaUtente(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credenziali credenziali = sessionData.getLoggedCredenziali();
		model.addAttribute("user", loggedUser);
		model.addAttribute("credenziali", credenziali);
		return "aggiornaUtente";
	}

	@PostMapping(value = "/aggiornaDatiUtente")
	public String aggiornaDatiUtente(Model model, @RequestParam("name") String name,
			@RequestParam("surname") String surname) {

		User user = sessionData.getLoggedUser();

		user.setName(name);
		user.setSurname(surname);
		userService.salvaUtente(user);
		return "redirect:/users/me";
	}

	@GetMapping(value = { "/admin" })
	public String admin(Model model) {
		User user = sessionData.getLoggedUser();
		model.addAttribute("user", user);
		return "admin";
	}

	@GetMapping(value = { "/admin/users" })
	public String listaUtenti(Model model) {

		User loggedUser = sessionData.getLoggedUser();
		List<Credenziali> tuttiCredenziali = this.credenzialiService.getTuttiCredenziali();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("listaCredenziali", tuttiCredenziali);
		return "tuttiUtenti";
	}

	@PostMapping(value = { "/admin/users/delete" })
	public String cancellaUtente(Model model, @ModelAttribute("id") Long id) {

		this.credenzialiService.cancellaCredenziali(id);
		return "redirect:/admin/users";
	}

}