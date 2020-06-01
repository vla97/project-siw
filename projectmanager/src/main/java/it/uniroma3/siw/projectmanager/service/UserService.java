package it.uniroma3.siw.projectmanager.service;

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
		public User signUp(User user) {
			User u = new User();
			userRepository.save(u);
			

			
			
		}
	


}
