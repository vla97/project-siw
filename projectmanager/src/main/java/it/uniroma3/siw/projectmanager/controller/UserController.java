package it.uniroma3.siw.projectmanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.TagService;
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
	private CredenzialiService credenzialiService;
	@Autowired 
	private ProjectValidator projectValidator;
	@Autowired 
	private TaskValidator taskValidator;
	@Autowired
	private TagService tagService;
	
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
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			projectService.salvaProgetto(project);
			model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
			model.addAttribute("projectsVisi", projectService.trovaProgettiMembro(loggedUser));
			return "progetto";
		}
		//model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		//model.addAttribute("projectsVisi", projectService.trovaProgettiMembro(loggedUser));
		return "formProgetto.html";
	}

	@RequestMapping(value = "/eliminaProgetto", method = RequestMethod.GET)
	public String eliminaProgetto(@ModelAttribute("id") Long id, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		projectService.cancellaProgetto(id);
		model.addAttribute("projects",projectService.ottieniProgettiProprietari(loggedUser));
		model.addAttribute("projectsVisi", projectService.trovaProgettiMembro(loggedUser));
		return "progetto.html";
	}

	@RequestMapping(value = "/visualizzaProgetto", method = RequestMethod.GET)
	public String visualizzaProgetto(@ModelAttribute("id") Long id, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("project", projectService.ottieniProgetto(id));
		model.addAttribute("tasks",taskService.ottieniTask( projectService.ottieniProgetto(id)));
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
		projectService.condiviProgetto(project, userService.ottieniUtentePerUsername(user.getUsername()));
		projectService.salvaProgetto(project);
		model.addAttribute("projects", projectService.ottieniProgettiProprietari(loggedUser));
		return "progetto.html";
	}
	

	
	@RequestMapping(value="/aggiungiTag", method=RequestMethod.GET)
	public String aggiungiTag
	(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2 ) {
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		model.addAttribute("tag", new Tag());
		return "formTag.html";

	}
	
	@RequestMapping(value="/aggiungi", method=RequestMethod.POST)
	public String aggiungi(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2, @ModelAttribute("tag") Tag tag ) {
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		
		tagService.salvaTag(tag);
		taskService.aggiungiTag(task, tag);
		taskService.salvaTask(task);
		model.addAttribute("tags", tagService.ottieniTag(task));
		
		return "tag.html";
	}
	
	
	@RequestMapping(value="/visualizzaTag", method=RequestMethod.GET)
	public String visTag(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2, @ModelAttribute("tag") Tag tag) {
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("tags", tagService.ottieniTag(task));
		return "tag.html";
	}
	
	
	@RequestMapping(value="/aggiungiTagProgetto", method=RequestMethod.GET)
	public String aggiungiTagProgetto
	(Model model, @ModelAttribute("id") Long id ) {
		Project project = projectService.ottieniProgetto(id);
		
		model.addAttribute("project", project);
		
		model.addAttribute("tag", new Tag());
		return "formTagProgetto.html";

	}
	
	@RequestMapping(value="/aggiungiTagP", method=RequestMethod.POST)
	public String aggiungiTagP(Model model, @ModelAttribute("id") Long id, @ModelAttribute("tag") Tag tag ) {
		
		
		Project project = projectService.ottieniProgetto(id);
		tagService.salvaTag(tag);
		//project.addTag(tag);
		projectService.aggiungiTag(project,tagService.ottieniTag(tag));
		projectService.salvaProgetto(project);
		model.addAttribute("project", project);
		model.addAttribute("tags", tagService.ottieniTag(project));
		
		
		
		return "tagProgetto.html";
	}
	
	
	/*
	 * @RequestMapping(value="/visualizzaTag", method=RequestMethod.GET) public
	 * String visTagProgetto(Model model, @ModelAttribute("id1") Long
	 * id1, @ModelAttribute("id2") Long id2, @ModelAttribute("tag") Tag tag) {
	 * Project project = projectService.ottieniProgetto(id1); Task task =
	 * taskService.ottieniTask(id2); model.addAttribute("project", project);
	 * model.addAttribute("tags", tagService.ottieniTag(task)); return "tag.html"; }
	 */

	@RequestMapping(value= {"/admin/users"},method=RequestMethod.GET)
	public String listaUtenti(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credenziali> tuttiCredenziali = this.credenzialiService.getTuttiCredenziali();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("listaCredenziali", tuttiCredenziali);
		return "tuttiUtenti";
	}
	@RequestMapping(value= {"/admin/users/{username}/delete"}, method=RequestMethod.POST)
	public String cancellaUtente(Model model, @PathVariable String username ) {
		this.credenzialiService.eliminaCredenziali(username);
		return "redirect:/admin/users";
	}

	
}