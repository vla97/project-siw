package it.uniroma3.siw.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;

import it.uniroma3.siw.projectmanager.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	@Transactional
	public Task ottieniTask(Long id) {
		Optional<Task> r = this.taskRepository.findById(id);
		return r.orElse(null);
	}
	
	@Transactional
	public Task ottieniTaskPerNome(String nome) {
		Optional<Task> r = this.taskRepository.findByNome(nome);
		return r.orElse(null);
	}
	
	@Transactional
	public List<Task> ottieniTask(Project project){
		List <Task> tasks = new ArrayList<>();
		Iterable <Task> r = taskRepository.findByProject(project);
		for(Task task : r)
			tasks.add(task);
		return tasks;
		
	}

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
	public Task salvaTask(Task task) {
		return this.taskRepository.save(task);
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
	
	/*@Transactional 
	public Optional<Task> getTask(Long id){
		taskRepository.findById(id);
	}*/

}
