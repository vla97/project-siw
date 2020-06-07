package it.uniroma3.siw.projectmanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	public Optional<Task> findByNome(String nome);
			

}
