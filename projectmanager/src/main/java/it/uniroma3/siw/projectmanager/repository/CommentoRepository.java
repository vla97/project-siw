package it.uniroma3.siw.projectmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.projectmanager.model.Commento;
import it.uniroma3.siw.projectmanager.model.Task;

public interface CommentoRepository extends CrudRepository<Commento, Long>{
	
	public List<Commento> findByTask(Task task);

}
