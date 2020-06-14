package it.uniroma3.siw.projectmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.projectmanager.controller.session.SessionData;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;
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
	SessionData sessionData;
	
	
	@RequestMapping(value = "/gestisciTask", method = RequestMethod.GET)
	public String task(Model model, @ModelAttribute("id") Long id) {
		
		Project project = projectService.ottieniProgetto(id);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		model.addAttribute("project", project);
		return "task";
	}

	@RequestMapping(value="/aggiungiTask", method = RequestMethod.GET)
	public String aggiungiTask(Model model, @ModelAttribute("id") Long id) {
		
		Project project = projectService.ottieniProgetto(id);	
		model.addAttribute("project",project);
		model.addAttribute("task", new Task());
		return "formTask.html";
	}
	
	@RequestMapping(value="/salvaTask", method=RequestMethod.POST)
	public String aggiungiTask(@ModelAttribute("id") Long id,
			@Valid @ModelAttribute("task") Task task,
			BindingResult taskBindingResult, Model model) {
		
		Project project = projectService.ottieniProgetto(id);
		taskValidator.validate(task, taskBindingResult);
		if(!taskBindingResult.hasErrors()) {
			//model.addAttribute("tasks", taskService.ottieniTask(project));
			task.setProject(project);
			taskService.salvaTask(task);
			model.addAttribute("project", project);
			model.addAttribute("tasks", taskService.ottieniTask(project));
			projectService.salvaProgetto(project);
			return "task";
		}
		projectService.salvaProgetto(project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		//return "formTask.html";
		return "redirect:/aggiungiTask?id="+project.getId();
	}
	
	@RequestMapping(value="/eliminaTask", method=RequestMethod.GET)
	public String aggiungiTask(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2 ) {
		
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		project.removeTask(task);		
		taskService.cancellaTask(task);	
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		projectService.salvaProgetto(project);
		return "task.html";
	}
	
	@RequestMapping(value="/aggiornaTask", method=RequestMethod.GET)
	public String aggiornaTask(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {
		
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		model.addAttribute("project", project);
		model.addAttribute("task", task);
		return "aggiornaTask.html";
	}
	
	@RequestMapping(value="/aggiorna", method=RequestMethod.POST)
	public String aggiorna(Model model, @ModelAttribute("id") Long id,@ModelAttribute("id1") Long id1,
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
	
	@RequestMapping(value="/condividiTask", method=RequestMethod.GET)
	public String condividiTask(Model model, @ModelAttribute("task") Task task, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2) {
				
		model.addAttribute("project",projectService.ottieniProgetto(id1));
		model.addAttribute("task", taskService.ottieniTask(id2));
		model.addAttribute("user", new User());
		return "condividiTaskForm";		
	}
	
	@RequestMapping(value="/condividiT", method=RequestMethod.POST)
	public String condividiT(Model model, @ModelAttribute("user") User user, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2 ) {
				
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		taskService.aggiungiMembro(task, userService.ottieniUtentePerUsername(user.getUsername()));
		taskService.salvaTask(task);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.ottieniTask(project));
		return "task";		
	}
	
	@RequestMapping(value="/commentaTask", method=RequestMethod.GET)
	public String commenta(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2 ) {
		
		model.addAttribute("project",projectService.ottieniProgetto(id1));
		model.addAttribute("task", taskService.ottieniTask(id2));
		return"formCommento";
	}
	
	@RequestMapping(value="/aggiungiCommento", method=RequestMethod.POST)
	public String aggiungiCommenta(Model model, @ModelAttribute("id1") Long id1, @ModelAttribute("id2") Long id2,
			@RequestParam("commento") String commento) {
		
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.ottieniProgetto(id1);
		Task task = taskService.ottieniTask(id2);
		task.setCommento(commento);		
		taskService.salvaTask(task);
		model.addAttribute("project", project);		
		model.addAttribute("tasks", taskService.ottieniTask(project));		
		return"dettagliProgetto";
	}
	
	
	
}
