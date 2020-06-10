package it.uniroma3.siw.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.UserService;

@Controller
public class MainController {
	@Autowired 
	private ProjectService projectService;
	@Autowired 
	private UserService userService;
	@Autowired
    SessionData sessionData;
	
	public MainController() {
	}
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String Index(Model model) {
		return "index";
	}
	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		User user = sessionData.getLoggedUser();
		model.addAttribute("user", user);
		return "home";
	}
	
	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public String admin(Model model) {
		User user = sessionData.getLoggedUser();
		model.addAttribute("user", user);
		return "admin";
	}
	
	@RequestMapping(value = { "/progetto" }, method = RequestMethod.GET)
	public String Progetto(Model model) {
		 User loggedUser = sessionData.getLoggedUser();
		 
		model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		model.addAttribute("projectsVisi", projectService.trovaProgettiMembro(loggedUser));
		return "progetto";
	}
	
	
}
