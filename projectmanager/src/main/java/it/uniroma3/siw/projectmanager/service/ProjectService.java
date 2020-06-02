package it.uniroma3.siw.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepository;

	
	@Transactional
	public Project creaProgetto(Project project) {
		return this.projectRepository.save(project);
	}
	
	@Transactional
	public void cancellaProgetto(Project project) {
		this.projectRepository.delete(project);
	}
	
	@Transactional
	public Project ottieniProgetto(Long id) {
		Optional<Project> r = this.projectRepository.findById(id);
		return r.orElse(null);
	}
	
	@Transactional
	public List<Project> ottieniProgetti(){
		List<Project> r = new ArrayList<>();
		Iterable<Project> i = this.projectRepository.findAll();
		for(Project project : i)
			r.add(project);
		return r;
	}
	
	@Transactional
	public List<Project> ottieniProgettiCondivisi(User user){
		List<Project> r = new ArrayList<>();
		Iterable<Project> i = this.projectRepository.findByVisibleProjects(user);
		for(Project project : i)
			r.add(project);
		return r;
	}
	
	
	@Transactional
	public void aggiornaProgetto( String nomeVecchio, String nomeNuovo) {
		 Project r = projectRepository.findByName(nomeVecchio);
		 r.setName(nomeNuovo);
		 projectRepository.save(r);
		 
		
	}
	
	@Transactional 
	public void condiviProgetto(Project progetto, User user) {
		progetto.addMembers(user);
		projectRepository.save(progetto);
		
	}
	
	@Transactional 
	public void aggiungiTag(Project progetto, Tag tag) {
		progetto.addTag(tag);
		
		
	}
	
	
	
}
