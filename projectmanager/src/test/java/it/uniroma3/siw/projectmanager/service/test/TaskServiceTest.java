package it.uniroma3.siw.projectmanager.service.test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.projectmanager.model.Task;

import it.uniroma3.siw.projectmanager.repository.TaskRepository;


public class TaskServiceTest {
	@Autowired
	private TaskRepository taskRepository;
	
	private Task t1;
	private Task t2;
	
	@BeforeAll
	public void setUp() {
		t1 = new Task("primo","task1", "nulla");
		t2 = new Task("secondo","task2", "nulla");
		taskRepository.save(t2);
	}
	
	@BeforeEach
	private void clean() {
		this.taskRepository.deleteAll();
	}
	
	@Test
	private void ottieniTaskPerId_Test() {
		taskRepository.save(t1);
		 Optional<Task> result = this.taskRepository.findById(t1.getId());
		 assertNotNull(result); 
	}
	
	@Test
	private void cancellaTask_Test() {
		taskRepository.save(t1);
		this.taskRepository.delete(t1);
		assertEquals(0, taskRepository.count());
	}
	
	@Test
	private void salvaTask_Test() {
		assertEquals(1,taskRepository.count());
	}
	
}