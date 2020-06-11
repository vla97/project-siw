package it.uniroma3.siw.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.repository.TagRepository;

@Service
public class TagService {
	@Autowired 
	TagRepository tagRepository;
	
	@Transactional 
	public void salvaTag(Tag tag) {
		tagRepository.save(tag);
	}
	
	@Transactional 
	public Tag ottieniTag(Tag tag) {
		Optional<Tag> opt = tagRepository.findById(tag.getId());
		return opt.orElse(null);
	}
	
	
	@Transactional 
	public List<Tag> ottieniTag(Task task) {
		List<Tag> r = new ArrayList<>();
		Iterable<Tag> i = tagRepository.findByTaskAssociati(task);
		for(Tag tag : i)
			r.add(tag);
		return r;
	}
	
	@Transactional 
	public List<Tag> ottieniTag(Project project) {
		List<Tag> r = new ArrayList<>();
		Iterable<Tag> i = tagRepository.findByProject(project);
		for(Tag tag : i)
			r.add(tag);
		return r;
	}
	

}
