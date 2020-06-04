package it.uniroma3.siw.projectmanager.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected CredenzialiRepository credenzialiRepository;
	
	@Transactional
	public Credenziali getCredenziali(Long id) {
		Optional<Credenziali> r = this.credenzialiRepository.findById(id);
		return r.orElse(null);
	}
	
	@Transactional
	public Credenziali getCredenziali(String username) {
		Optional<Credenziali> r = this.credenzialiRepository.findByUsername(username);
		return r.orElse(null);
	}
	
	@Transactional
	public Credenziali saveCredenziali(Credenziali credenziali) {
		credenziali.setRole(Credenziali.DEFAULT_ROLE);
		credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
		return this.credenzialiRepository.save(credenziali);
	}
}