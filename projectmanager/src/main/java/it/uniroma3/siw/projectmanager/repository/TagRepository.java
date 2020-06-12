package it.uniroma3.siw.projectmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.Tag;
import it.uniroma3.siw.projectmanager.model.Task;

public interface TagRepository extends CrudRepository<Tag, Long> {
	public List<Tag> findByTaskAssociati(Task task);
	public List<Tag> findByProjectOwner(Project project);
	

}
