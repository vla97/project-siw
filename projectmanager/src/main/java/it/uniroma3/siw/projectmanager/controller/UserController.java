package it.uniroma3.siw.projectmanager.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.TaskService;
import it.uniroma3.siw.projectmanager.service.UserService;

@Controller
public class UserController {
	// private UserValidator userValidator;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;
	@Autowired
	private TaskService taskService;
	
	@Autowired
	SessionData sessionData;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {

		return "home";
	}

	@RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
	public String me(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credenziali credenziali = sessionData.getLoggedCredenziali();
		System.out.println(credenziali.getPassword());
		model.addAttribute("user", loggedUser);
		model.addAttribute("credenziali", credenziali);

		return "user";
	}

	@RequestMapping(value = "/creaProgetto", method = RequestMethod.GET)
	public String inserisciDati(Model model) {
		
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", new Project());
		return "formProgetto.html";
	}

	@RequestMapping(value = "/salvaProgetto", method = RequestMethod.POST)
	public String salvaProgetto(@ModelAttribute("project") Project project, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		project.setOwner(loggedUser);
		projectService.salvaProgetto(project);
		model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		return "progetto.html";
	}

	@RequestMapping(value = "/eliminaProgetto", method = RequestMethod.GET)
	public String eliminaProgetto(@ModelAttribute("id") Long id, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		projectService.cancellaProgetto(id);
		model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		return "progetto.html";
	}

	@RequestMapping(value = "/visualizzaProgetto", method = RequestMethod.GET)
	public String visualizzaProgetto(@ModelAttribute("id") Long id, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("project", projectService.ottieniProgetto(id));
		model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		return "specificaProgetto.html";
	}
	
	@RequestMapping(value="/condividiProgetto", method = RequestMethod.GET)
	public String condividiProgetto(Model model, @ModelAttribute("id") Long id ) {
		model.addAttribute("project", projectService.ottieniProgetto(id));
		model.addAttribute("user", new User());
		return "condividiForm.html";
	}
	
	@RequestMapping(value="/condividi", method = RequestMethod.POST)
	public String condividi(Model model, @ModelAttribute("user") User user, @ModelAttribute("id") Long id ) {
		Project project = projectService.ottieniProgetto(id);
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("project", project);
		projectService.condiviProgetto(project, userService.ottieniUtentePerId(user.getId()));
		projectService.salvaProgetto(project);
		model.addAttribute("projects", projectService.ottieniProgettiProprietari(loggedUser));
		return "progetto.html";
	}
	
	@RequestMapping(value="/aggiungiTask", method = RequestMethod.GET)
	public String aggiungiTask(Model model, @ModelAttribute("id") Long id) {
		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project",project);
		model.addAttribute("task", new Task());
		return "formTask.html";
	}
	
	@RequestMapping(value="/salvaTask", method=RequestMethod.POST)
	public String aggiungiTask(Model model, @ModelAttribute("task") Task task, @ModelAttribute("id") Long id ) {
		Project project = projectService.ottieniProgetto(id);
		
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		taskService.aggiungiTask(project,taskService.ottieniTask(id) );
		taskService.salvaTask(task);
		project.addTask( taskService.ottieniTask(id));
		//projectService.salvaProgetto(project);
		return "specificaProgetto.html";
	}
	
	@RequestMapping(value="/eliminaTask", method=RequestMethod.POST)
	public String aggiungiTask(Model model, @ModelAttribute("id") Long id1, @ModelAttribute("id") Long id2 ) {
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		
		taskService.cancellaTask(task);
		model.addAttribute("project", project);
		projectService.salvaProgetto(project);
		return "specificaProgetto.html";
	}
	
	@RequestMapping(value="/aggiornaTask", method=RequestMethod.POST)
	public String aggiornaTask(Model model, @ModelAttribute("id") Long id1, @ModelAttribute("id") Long id2 ) {
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		taskService.aggiornaTask();
		model.addAttribute("project", project);
		model.addAttribute("tasks", project.getTasks());
	
		projectService.salvaProgetto(project);
		return "specificaProgetto.html";
	}
	
	@RequestMapping(value="/condividiTask", method=RequestMethod.POST)
	public String condividiTask(Model model, @ModelAttribute("task") Task task, @ModelAttribute("id") Long id ) {
		Project project = projectService.ottieniProgetto(id);
		project.addTask(task);
		model.addAttribute("project", project);
		model.addAttribute("tasks", project.getTasks());
	
		projectService.salvaProgetto(project);
		return "specificaProgetto.html";
	}

}
