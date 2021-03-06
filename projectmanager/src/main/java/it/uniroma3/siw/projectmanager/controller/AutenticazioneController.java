package it.uniroma3.siw.projectmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;
import static it.uniroma3.siw.projectmanager.model.Credenziali.ADMIN_ROLE;
import static it.uniroma3.siw.projectmanager.model.Credenziali.DEFAULT_ROLE;

@Controller
public class AutenticazioneController {

	@Autowired
	CredenzialiService credenzialiService;

	@Autowired
	UserValidator userValidator;

	@Autowired
	CredenzialiValidator credenzialiValidator;

	@Autowired
	private SessionData sessionData;

	@GetMapping(value = { "/users/register" })
	public String showRegisterForm(Model model) { 
		model.addAttribute("userForm", new User());
		model.addAttribute("credenzialiForm", new Credenziali());
		return "registerUser";
	}

	@PostMapping(value = { "/users/register" })
	public String registerUser(@Valid @ModelAttribute("userForm") User user, BindingResult userBindingResult,
			@Valid @ModelAttribute("credenzialiForm") Credenziali credenziali, BindingResult credenzialiBindingResult,
			Model model) {

		this.userValidator.validate(user, userBindingResult);
		this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
		if (!userBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
			credenziali.setUser(user);
			credenzialiService.saveCredenziali(credenziali);
			return "index";
		}
		return "registerUser";
	}

	@GetMapping(value = "/login")
	public String loginPage(HttpServletRequest httpServletRequest, Model model) {
		if (httpServletRequest.isUserInRole(ADMIN_ROLE)) {
			return "admin";
		} else if (httpServletRequest.isUserInRole(DEFAULT_ROLE)) {
			return "home";
		} else {
			return "login";
		}
	}

	@GetMapping(value = "/logout")
	public String logout(Model model) {
		this.sessionData.clear();
		return "index";
	}

}