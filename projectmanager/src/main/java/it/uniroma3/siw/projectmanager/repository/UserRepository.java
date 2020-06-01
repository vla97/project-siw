package it.uniroma3.siw.projectmanager.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
}
