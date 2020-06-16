package it.uniroma3.siw.projectmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	public Optional<Task> findByNome(String nome);
	public List<Task> findByProject(Project project);
	public List<Task> findByTagAssociati(Tag tag);
	public List<Task> findByMembers(User user);
			

}
