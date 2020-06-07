package it.uniroma3.siw.projectmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;

@Controller
public class AutenticazioneController {
	
	@Autowired
	CredenzialiService credenzialiService;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	CredenzialiValidator credenzialiValidator;
	
	@RequestMapping(value = {"/users/register"}, method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credenzialiForm", new Credenziali());
		return "registerUser";
	}
	
	@RequestMapping(value = {"/users/register"}, method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("userForm") User user,
								BindingResult userBindingResult,
								@Valid @ModelAttribute("credenzialiForm") Credenziali credenziali,
								BindingResult credenzialiBindingResult, Model model) {
	
			this.userValidator.validate(user, userBindingResult);
			this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
			
			
			if(!userBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
				credenziali.setUser(user);
				credenzialiService.saveCredenziali(credenziali);
				return "index";
			}
			return "registerUser";
	}
}