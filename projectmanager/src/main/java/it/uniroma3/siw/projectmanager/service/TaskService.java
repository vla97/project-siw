package it.uniroma3.siw.projectmanager.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.repository.ProjectRepository;
import it.uniroma3.siw.projectmanager.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	@Transactional
	public void aggiungiTask(Project progetto, Task task) {
		progetto.addTask(task);
		}

	@Transactional
	public void aggiornaTask() {
	}

	@Transactional
	public void cancellaTask(Task task) {
		this.taskRepository.delete(task);
	}
	
	@Transactional
	public boolean setTaskCompleto(Task task) {
		task.setIsCompleto(true);	
		this.taskRepository.save(task);
		return task.getIsCompleto();
	}
	
	@Transactional 
	public void aggiungiTag(Task task, Tag tag) {
		task.aggiungiTag(tag);
		taskRepository.save(task);
	}
	
	@Transactional
	public void aggiungiCommento(Task task, String commento) {
		task.setCommento(commento);
		taskRepository.save(task);
		
		
	}

}
