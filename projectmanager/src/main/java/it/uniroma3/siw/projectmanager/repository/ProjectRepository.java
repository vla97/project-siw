package it.uniroma3.siw.projectmanager.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>{

}
