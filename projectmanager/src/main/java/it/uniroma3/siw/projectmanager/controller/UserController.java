package it.uniroma3.siw.projectmanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.service.ProjectService;


@Controller
public class UserController {
	// private UserValidator userValidator;
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/creaProgetto", method = RequestMethod.GET)
	public String inserisciDati(Model model) {
		model.addAttribute("project", new Project());	
		return "formProgetto.html";
	}
	
	@RequestMapping(value = "/salva", method = RequestMethod.GET)
	public String salvaProgetto(Model model) {
		Project project = (Project) model.getAttribute("project");
		projectService.salvaProgetto(project);
		return "index.html";
		//
	}


}
