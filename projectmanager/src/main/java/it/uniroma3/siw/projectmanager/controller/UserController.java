package it.uniroma3.siw.projectmanager.controller;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.UserService;


@Controller
public class UserController {
	// private UserValidator userValidator;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/creaProgetto", method = RequestMethod.GET)
	public String inserisciDati(Model model) {
		model.addAttribute("project", new Project());	
		return "formProgetto.html";
	}
	
	
	@RequestMapping(value = "/salvaProgetto", method = RequestMethod.POST)
	public String salvaProgetto(@ModelAttribute("project") Project project, Model model) {
		projectService.salvaProgetto(project);
		model.addAttribute("projects",projectService.ottieniProgetti());
		return "index.html";
	}
	
	@RequestMapping(value = "/eliminaProgetto", method = RequestMethod.GET)
	public String eliminaProgetto(@ModelAttribute("id") Long id, Model model) {
		projectService.cancellaProgetto(id);
		model.addAttribute("projects",projectService.ottieniProgetti());
		return "index.html";
	}
	
	@RequestMapping(value="/visualizzaProgetto", method = RequestMethod.GET)
	public String visualizzaProgetto(@ModelAttribute("id") Long id, Model model) {
		model.addAttribute("project", projectService.ottieniProgetto(id));
		return "specificaProgetto.html";
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String tornaAllaHome(Model model) {
		model.addAttribute("projects", projectService.ottieniProgetti());
		return "index.html";
	}
	
	@RequestMapping(value="/visualizzaProfilo", method = RequestMethod.GET)
	public String visualizzaProfilo(Model model) {
		model.addAttribute("user",new User());		//da rivedere
		return "profiloUtente.html";
		
	}


}
