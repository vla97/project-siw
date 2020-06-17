package it.uniroma3.siw.projectmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User ottieniUtentePerId(Long id) {
		Optional<User> r = this.userRepository.findById(id);
		return r.orElse(null);
	}

	@Transactional
	public User ottieniUtentePerUsername(String username) {
		Optional<User> r = this.userRepository.findByUsername(username);
		return r.orElse(null);
	}

	@Transactional
	public User salvaUtente(User user) {
		return this.userRepository.save(user);
	}

	@Transactional
	public List<User> ottieniUtenti() {
		List<User> r = new ArrayList<>();
		Iterable<User> i = this.userRepository.findAll();
		for (User user : i)
			r.add(user);
		return r;
	}

}
