package it.uniroma3.siw.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.TagService;
import it.uniroma3.siw.projectmanager.service.TaskService;

@Controller
public class TagController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TagService tagService;

	@Autowired
	SessionData sessionData;
	
	
	
	@RequestMapping(value = "/aggiungiTag", method = RequestMethod.GET)
	public String aggiungiTag(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {
		
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		model.addAttribute("tag", new Tag());
		return "formTag.html";
	}

	@RequestMapping(value = "/aggiungi", method = RequestMethod.POST)
	public String aggiungi(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@ModelAttribute("tag") Tag tag) {
		
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		tagService.salvaTag(tag);
		taskService.aggiungiTag(task, tag);
		taskService.salvaTask(task);
		model.addAttribute("tags", tagService.ottieniTag(task));
		return "tag.html";
	}

	@RequestMapping(value = "/visualizzaTag", method = RequestMethod.GET)
	public String visTag(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@ModelAttribute("tag") Tag tag) {
		
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("tags", tagService.ottieniTag(task));
		return "tag.html";
	}

	@RequestMapping(value = "/aggiungiTagProgetto", method = RequestMethod.GET)
	public String aggiungiTagProgetto(Model model, @ModelAttribute("id") Long id) {
		
		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("tag", new Tag());
		return "formTagProgetto.html";
	}

	@RequestMapping(value = "/aggiungiTagP", method = RequestMethod.POST)
	public String aggiungiTagP(Model model, @ModelAttribute("id") Long id, @ModelAttribute("tag") Tag tag) {

		Project project = projectService.ottieniProgetto(id);
		tag.setProjectOwner(project);
		tagService.salvaTag(tag);
		projectService.salvaProgetto(project);
		model.addAttribute("project", project);
		model.addAttribute("tags", tagService.ottieniTag(tag.getProjectOwner()));
		return "tagProgetto.html";
	}

}
