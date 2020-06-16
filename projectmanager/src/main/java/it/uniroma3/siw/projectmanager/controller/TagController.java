package it.uniroma3.siw.projectmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	private TagValidator tagValidator;

	@Autowired
	SessionData sessionData;

	@GetMapping(value = "/aggiungiTag")
	public String aggiungiTag(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		model.addAttribute("tag", new Tag());
		return "formTag.html";
	}

	@PostMapping(value = "/aggiungi")
	public String aggiungi(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@Valid @ModelAttribute("tag") Tag tag, BindingResult tagBindingResult) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		tagValidator.validate(tag, tagBindingResult);
		if (!tagBindingResult.hasErrors()) {
			model.addAttribute("project", project);
			tagService.salvaTag(tag);
			taskService.aggiungiTag(task, tag);
			taskService.salvaTask(task);
			model.addAttribute("tags", tagService.ottieniTag(task));
			return "tag.html";
		}
		return "formTag";
	}

	@GetMapping(value = "/visualizzaTag")
	public String visTag(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@ModelAttribute("tag") Tag tag) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("tags", tagService.ottieniTag(task));
		return "tag.html";
	}

	@GetMapping(value = "/aggiungiTagProgetto")
	public String aggiungiTagProgetto(Model model, @ModelAttribute("id") Long id) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("tag", new Tag());
		return "formTagProgetto.html";
	}

	@PostMapping(value = "/aggiungiTagP")
	public String aggiungiTagP(Model model, @ModelAttribute("id") Long id, @ModelAttribute("tag") Tag tag,
			BindingResult tagBindingResult) {

		Project project = projectService.ottieniProgetto(id);
		tagValidator.validate(tag, tagBindingResult);
		if (!tagBindingResult.hasErrors()) {
			tag.setProjectOwner(project);
			tagService.salvaTag(tag);
			projectService.salvaProgetto(project);
			model.addAttribute("project", project);
			model.addAttribute("tags", tagService.ottieniTag(tag.getProjectOwner()));

			return "tagProgetto.html";
		}
		return "formTagProgetto";
	}

}
