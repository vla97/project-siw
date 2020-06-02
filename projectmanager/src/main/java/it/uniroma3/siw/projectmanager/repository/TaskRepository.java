package it.uniroma3.siw.projectmanager.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
			

}
