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
	public Project salvaProgetto(Project project) {
		return this.projectRepository.save(project);
	}

	@Transactional
	public void cancellaProgetto(Long id) {
		this.projectRepository.deleteById(id);
	}

	@Transactional
	public Project ottieniProgetto(Long id) {
		Optional<Project> r = this.projectRepository.findById(id);
		return r.orElse(null);
	}

	@Transactional
	public Project ottieniProgetto(String name) {
		Optional<Project> r = this.projectRepository.findByName(name);
		return r.orElse(null);
	}

	@Transactional
	public List<Project> ottieniProgetti() {
		List<Project> r = new ArrayList<>();
		Iterable<Project> i = this.projectRepository.findAll();
		for (Project project : i)
			r.add(project);
		return r;
	}

	@Transactional
	public Project condiviProgetto(Project progetto, User user) {
		progetto.addMember(user);
		return this.projectRepository.save(progetto);

	}

	@Transactional
	public void aggiungiTag(Project progetto, Tag tag) {
		progetto.addTag(tag);
		projectRepository.save(progetto);
	}

	@Transactional
	public List<Project> ottieniProgettiProprietari(User owner) {
		List<Project> r = new ArrayList<>();
		Iterable<Project> i = this.projectRepository.findByOwner(owner);
		for (Project project : i)
			r.add(project);
		return r;
	}

	@Transactional
	public List<Project> trovaProgettiMembro(User user) {
		List<Project> r = new ArrayList<>();
		Iterable<Project> projects = this.projectRepository.findByMembers(user);
		for (Project project : projects)
			r.add(project);
		return r;

	}

}
