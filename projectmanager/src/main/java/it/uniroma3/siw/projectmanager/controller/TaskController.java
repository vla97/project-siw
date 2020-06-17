package it.uniroma3.siw.projectmanager.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.TaskService;
import it.uniroma3.siw.projectmanager.service.UserService;

@Controller
public class TaskController {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskValidator taskValidator;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	SessionData sessionData;

	@GetMapping(value = "/gestisciTask/{id}")
	public String task(Model model, @ModelAttribute("id") Long id) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		model.addAttribute("project", project);
		return "task";
	}

	@GetMapping(value = "/aggiungiTask/{id}")
	public String aggiungiTask(Model model, @ModelAttribute("id") Long id) {

		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("project", project);
		model.addAttribute("task", new Task());
		return "formTask.html";
	}

	@PostMapping(value = "/salvaTask")
	public String aggiungiTask(@ModelAttribute("id") Long id, @Valid @ModelAttribute("task") Task task,
			BindingResult taskBindingResult, Model model) {

		Project project = projectService.ottieniProgetto(id);
		taskValidator.validate(task, taskBindingResult);
		if (!taskBindingResult.hasErrors()) {

			task.setProject(project);
			taskService.salvaTask(task);
			model.addAttribute("project", project);
			model.addAttribute("tasks", taskService.ottieniTask(project));
			projectService.salvaProgetto(project);
			return "task";
		}
		projectService.salvaProgetto(project);
		model.addAttribute("tasks", taskService.ottieniTask(project));

		return "redirect:/aggiungiTask/" + project.getId();
	}

	@GetMapping(value = "/eliminaTask")
	public String aggiungiTask(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		project.removeTask(task);
		taskService.cancellaTask(task);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		projectService.salvaProgetto(project);
		return "task.html";
	}

	@GetMapping(value = "/aggiornaTask")
	public String aggiornaTask(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		return "aggiornaTask.html";
	}

	@PostMapping(value = "/aggiorna")
	public String aggiorna(Model model, @ModelAttribute("id") Long id, @ModelAttribute("id1") Long id1,
			@RequestParam("nome") String nNome, @RequestParam("descrizione") String nDescrizione) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id);
		task.setNome(nNome);
		task.setDescrizione(nDescrizione);
		taskService.salvaTask(task);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		return "task.html";
	}

	@GetMapping(value = "/condividiTask/{id1}/{id2}")
	public String condividiTask(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		model.addAttribute("project", projectService.ottieniProgetto(id1));
		model.addAttribute("task", taskService.ottieniTask(id2));
		List<Credenziali> tuttiCredenziali = this.credenzialiService.getTuttiCredenziali();
		model.addAttribute("listaCredenziali", tuttiCredenziali);
		return "condividiTask";
	}

	@PostMapping(value = "/condividiT")
	public String condividiT(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@ModelAttribute("id3") Long id3) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		taskService.aggiungiMembro(task, userService.ottieniUtentePerId(id3));
		taskService.salvaTask(task);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		return "task";
	}

	@GetMapping(value = "/commentaTask")
	public String commenta(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		model.addAttribute("project", projectService.ottieniProgetto(id1));
		model.addAttribute("task", taskService.ottieniTask(id2));
		return "formCommento";
	}

	@PostMapping(value = "/aggiungiCommento")
	public String aggiungiCommenta(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@RequestParam("commento") String commento) {

		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		task.setCommento(commento);
		taskService.salvaTask(task);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		return "dettagliProgetto";
	}

	@PostMapping(value = "/setStato")
	public String setStato(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {

		Project project = projectService.ottieniProgetto(id1);
		model.addAttribute("project", projectService.ottieniProgetto(id1));
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("flag", task.getIsCompleto());
		taskService.setTaskCompleto(task);
		taskService.salvaTask(task);
		model.addAttribute("task", task);

		return "redirect:/gestisciTask/" + project.getId();

	}
}
