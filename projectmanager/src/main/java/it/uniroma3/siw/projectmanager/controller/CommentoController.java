package it.uniroma3.siw.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Commento;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.repository.CommentoRepository;
import it.uniroma3.siw.projectmanager.service.CommentoService;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.TaskService;

@Controller
public class CommentoController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	CommentoService commentoService;
	
	@Autowired
	CommentoRepository commentoRepository;
	
	@Autowired
	SessionData sessionData;

	@GetMapping(value = "/commentaTask/{id1}/{id2}")
	public String aggiungiCommento(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {
		model.addAttribute("project", projectService.ottieniProgetto(id1));
		model.addAttribute("task", taskService.ottieniTask(id2));
		model.addAttribute("commento", new Commento());
		return "formCommento";
	}
	
	@PostMapping(value = "/aggiungiCommento/{id1}/{id2}")
	public String aggiungiCommento(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@ModelAttribute("commento") Commento commento) {

		Project project = projectService.ottieniProgetto(id1);
		User loggedUser = sessionData.getLoggedUser();
		Task task = taskService.ottieniTask(id2);
		commento.setTask(task);
		commento.setUser(loggedUser);
		taskService.aggiungiCommento(task, commento);
	
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTaskCondiviso(project, loggedUser));
		
		return "dettagliProgetto";
	}
}
