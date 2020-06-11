package it.uniroma3.siw.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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
	@Transactional
	public List<Credenziali> getTuttiCredenziali(){
		Iterable<Credenziali> itera=this.credenzialiRepository.findAll();
		ArrayList<Credenziali> lista=new ArrayList<>();
		for(Credenziali c:itera) {
			lista.add(c);
		}
		return lista;
	}
	@Transactional
	public void cancellaCredenziali(Long id) {
		Credenziali credenziali = this.getCredenziali(id);
		
		this.credenzialiRepository.delete(credenziali);
		
	}
	@Transactional
	public List<Credenziali> eliminaCredenziali(String username){
		Iterable<Credenziali> credenziali = credenzialiRepository.findAll();
		List<Credenziali> result  = new ArrayList<>();
		//Optional<Credenziali> credenziali1 = this.credenzialiRepository.findByUsername(username);
		for (Credenziali credenziali2 : credenziali) 
			if(!credenziali2.getUsername().equals(username)) {
				result.add(credenziali2);
			}


		return result;


	}
}