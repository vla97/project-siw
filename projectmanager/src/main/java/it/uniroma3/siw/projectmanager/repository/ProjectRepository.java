package it.uniroma3.siw.projectmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long>{
	public Project findByName(String name);
	public List<Project> findByOwner(User owner);
	


}
