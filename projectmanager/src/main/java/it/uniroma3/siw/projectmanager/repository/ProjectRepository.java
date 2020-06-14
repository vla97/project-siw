package it.uniroma3.siw.projectmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long>{
	public Optional<Project> findByName(String name);
	
	public List<Project> findByOwner(User owner);
	public List<Project> findByMembers(User user);
	
	


}
