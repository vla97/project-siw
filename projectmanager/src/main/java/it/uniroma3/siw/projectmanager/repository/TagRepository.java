package it.uniroma3.siw.projectmanager.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

}
