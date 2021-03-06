package it.uniroma3.siw.projectmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;
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
	private CredenzialiService credenzialiService;

	@Autowired
	SessionData sessionData;

	@GetMapping(value = { "/progetto" })
	public String Progetto(Model model) {
		User loggedUser = sessionData.getLoggedUser();

		model.addAttribute("projects", projectService.ottieniProgettiProprietari(loggedUser));
		model.addAttribute("projectsVisi", projectService.trovaProgettiMembro(loggedUser));
		return "progetto";
	}

	@GetMapping(value = "/creaProgetto")
	public String inserisciDati(Model model) {

		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", new Project());
		return "formProgetto.html";
	}

	@PostMapping(value = "/salvaProgetto")
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

	@GetMapping(value = "/eliminaProgetto")
	public String eliminaProgetto(@ModelAttribute("id") Long id, Model model) {

		projectService.cancellaProgetto(id);
		return "redirect:/progetto";
	}

	@GetMapping(value = "/aggiornaProgetto/{id}")
	public String aggiornaProgetto(@ModelAttribute("id") Long id, Model model) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		return "aggiornaProgetto.html";
	}

	@PostMapping(value = "/aggiornaP/{id}")
	public String aggiornaP(@RequestParam("name") String name, @ModelAttribute("id") Long id, Model model) {

		Project project = projectService.ottieniProgetto(id);
		project.setName(name);
		projectService.salvaProgetto(project);
		model.addAttribute("project", project);
		return "redirect:/visualizzaProgetto/" + project.getId();

	}

	@GetMapping(value = "/visualizzaProgetto/{id}")
	public String visualizzaProgetto(@PathVariable Long id, Model model) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(projectService.ottieniProgetto(id)));
		return "specificaProgetto.html";
	}

	@GetMapping(value = "/condividiProgetto/{id}")
	public String condividiP(Model model, @ModelAttribute("id") Long id) {

		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("project", projectService.ottieniProgetto(id));
		List<Credenziali> tuttiCredenziali = this.credenzialiService.getTuttiCredenzialiTranneUser(loggedUser);
		model.addAttribute("listaCredenziali", tuttiCredenziali);
		return "condividiProgetto";
	}

	@PostMapping(value = "/condividi")
	public String condividi(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		Project project = projectService.ottieniProgetto(id1);
		if (project.hasMember(userService.ottieniUtentePerId(id2)))
			project.removeMember(userService.ottieniUtentePerId(id2));

		else
			projectService.condiviProgetto(project, userService.ottieniUtentePerId(id2));

		model.addAttribute("project", project);
		projectService.salvaProgetto(project);
		return "redirect:/condividiProgetto/" + project.getId();
	}

	@GetMapping(value = "/visualizzaDettagli/{id}")
	public String visualizzaDettagli(Model model, @ModelAttribute("id") Long id) {

		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTaskCondiviso(project, loggedUser));
		return "dettagliProgetto.html";
	}

}
