package it.uniroma3.siw.projectmanager.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.Commento;
import it.uniroma3.siw.projectmanager.model.Task;
import it.uniroma3.siw.projectmanager.repository.CommentoRepository;

@Service
public class CommentoService {

	@Autowired
	CommentoRepository commentoRepository;

	@Transactional
	public Commento salvaCommento(Commento commento) {
		return this.commentoRepository.save(commento);
	}

	@Transactional
	public List<Commento> tuttiCommenti(Task task) {
		return this.commentoRepository.findByTask(task);
	}

}
