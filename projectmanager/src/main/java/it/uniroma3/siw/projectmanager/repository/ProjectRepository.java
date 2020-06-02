package it.uniroma3.siw.projectmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long>{
	public List<Project> findByVisibleProjects(User user);
	public Project findByName(String name);

}
