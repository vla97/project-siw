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
		
		//trova utente per id
		@Transactional
		public User getUser(long id) {
			Optional<User> r = this.userRepository.findById(id);
			return r.orElse(null);
		}
		
		//Trova utente per username
		
		
		@Transactional
		public User getUser(String username) {
			
			Optional<User> r = this.userRepository.findByUserName(username);
			return r.orElse(null);
		}
			
		// Salva utente nel DB
		
		@Transactional
		public User saveUser(User user) {
			return this.userRepository.save(user);
		}
		
		//visualizza tutti i utenti
		
		@Transactional
		public List<User> getAllUsers(){
			List<User> r = new ArrayList<>();
			Iterable<User>  i = this.userRepository.findAll();
			for(User user : i)
				r.add(user);
			return r;
		}
		


	
		//@Transactional
		//public User signUp(User user) {
		//	User u = new User();
		//	userRepository.save(u);
		//}
	


}
