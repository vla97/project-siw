package it.uniroma3.siw.projectmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public List<User> findByVisibleProjects(Project project);
	public Optional<User> findByUsername(String username);

	
}
