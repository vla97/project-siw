package it.uniroma3.siw.projectmanager.service.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.repository.UserRepository;

class UserServiceTest {
	@Autowired
	private UserRepository userRepository;
	
	
	private User u1;
	private User u2;
	
	@BeforeAll
	public void setUp() {
		u1 = new User("and98","1234", "andrea", "moscato");
		u2 = new User("vlesh","5678", "vladyslav", "petriv");
	}
	
	@BeforeEach
	private void clean() {
		this.userRepository.deleteAll();
	}
	
	
	@Test
	private void ottieniUtentePerId_Test(Long id) {
		Optional<User> r = this.userRepository.findById(id);
		assertNotNull(r);
	}
	
	@Test
	private void salvaUtente_Test() {
		this.userRepository.save(u1);
		assertEquals(u1,userRepository.findByUsername("and98"));
	}
	
	@Test
	private void ottieniUtenti_Test(){
		this.userRepository.save(u1);
		this.userRepository.save(u2);
		
		List<User> users = (List<User>) userRepository.findAll();
		assertEquals(2, users.size());
		
	}
	

}
