package it.uniroma3.siw.projectmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.TaskService;
import it.uniroma3.siw.projectmanager.service.UserService;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProjectValidator projectValidator;

	@Autowired
	SessionData sessionData;
	
	
	@RequestMapping(value = { "/progetto" }, method = RequestMethod.GET)
	public String Progetto(Model model) {
		 User loggedUser = sessionData.getLoggedUser();
		 
		model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		model.addAttribute("projectsVisi", projectService.trovaProgettiMembro(loggedUser));
		return "progetto";
	}

	@RequestMapping(value = "/creaProgetto", method = RequestMethod.GET)
	public String inserisciDati(Model model) {

		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", new Project());
		return "formProgetto.html";
	}

	@RequestMapping(value = "/salvaProgetto", method = RequestMethod.POST)
	public String salvaProgetto(@Valid @ModelAttribute("project") Project project, Model model,
			BindingResult projectBindingResult) {

		User loggedUser = sessionData.getLoggedUser();
		projectValidator.validate(project, projectBindingResult);
		if (!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			projectService.salvaProgetto(project);
			return "redirect:/progetto";
		}
		return "formProgetto.html";
	}

	@RequestMapping(value = "/eliminaProgetto", method = RequestMethod.GET)
	public String eliminaProgetto(@ModelAttribute("id") Long id, Model model) {

		projectService.cancellaProgetto(id);
		return "redirect:/progetto";
	}

	@RequestMapping(value = "/aggiornaProgetto", method = RequestMethod.GET)
	public String aggiornaProgetto(@ModelAttribute("id") Long id, Model model) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		return "aggiornaProgetto.html";
	}

	@RequestMapping(value = "/aggiornaP", method = RequestMethod.POST)
	public String aggiornaP(@ModelAttribute("id") Long id, Model model, @RequestParam("name") String name) {

		Project project = projectService.ottieniProgetto(id);
		project.setName(name);
		projectService.salvaProgetto(project);
		model.addAttribute("project", project);
		return "redirect:/visualizzaProgetto/" + project.getId();
	}

	@RequestMapping(value = "/visualizzaProgetto/{id}", method = RequestMethod.GET)
	public String visualizzaProgetto(@PathVariable Long id, Model model) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(projectService.ottieniProgetto(id)));
		return "specificaProgetto.html";
	}

	@RequestMapping(value = "/condividiProgetto", method = RequestMethod.GET)
	public String condividiProgetto(Model model, @ModelAttribute("id") Long id) {

		model.addAttribute("project", projectService.ottieniProgetto(id));
		model.addAttribute("user", new User());
		return "condividiForm.html";
	}

	@RequestMapping(value = "/condividi", method = RequestMethod.POST)
	public String condividi(Model model, @ModelAttribute("user") User user, @ModelAttribute("id") Long id) {

		Project project = projectService.ottieniProgetto(id);
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("project", project);
		projectService.condiviProgetto(project, userService.ottieniUtentePerUsername(user.getUsername()));
		projectService.salvaProgetto(project);
		model.addAttribute("projects", projectService.ottieniProgettiProprietari(loggedUser));
		return "progetto.html";
	}

	@RequestMapping(value = "/visualizzaDettagli", method = RequestMethod.GET)
	public String visualizzaDettagli(Model model, @ModelAttribute("id") Long id) {

		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(loggedUser));
		return "dettagliProgetto.html";
	}

}
