package it.uniroma3.siw.projectmanager;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.projectmanager.model.Credenziali;
import it.uniroma3.siw.projectmanager.model.Project;
import it.uniroma3.siw.projectmanager.model.User;
import it.uniroma3.siw.projectmanager.repository.ProjectRepository;
import it.uniroma3.siw.projectmanager.repository.TagRepository;
import it.uniroma3.siw.projectmanager.repository.TaskRepository;
import it.uniroma3.siw.projectmanager.repository.UserRepository;
import it.uniroma3.siw.projectmanager.service.CredenzialiService;
import it.uniroma3.siw.projectmanager.service.ProjectService;
import it.uniroma3.siw.projectmanager.service.UserService;



@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectmanagerApplicationTests {
  
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private TagRepository tagRepository;
  @Autowired
  private CredenzialiService credenzialiService;
  @Autowired
  private UserService userService;
  @Autowired
  private ProjectService projectService;
  
  
  
  @Before
  public void deletAll() {
    System.out.println("Elimination of content on the repositories...");
    this.userRepository.deleteAll();
    this.projectRepository.deleteAll();
    this.taskRepository.deleteAll();
    System.out.println("Done.");
  }
  
  

  @Test
  void test() {
    //creazione due utenti
    User u1 = new User("and98","and98","Andrea","Moscato");
    Credenziali c1 = new Credenziali("and98","and98");
    c1.setUser(u1);
    
    c1=credenzialiService.saveCredenziali(c1);
    assertEquals(c1.getId().longValue(), 1L);
    assertEquals(u1.getId().longValue(), 2L);
    assertEquals(c1.getUser().getName(), "Andrea");
    
    User u2 = new User("vla98","vla98","Vladyslav","Petriv");
    Credenziali c2 = new Credenziali("vla98","vla98");
    c2.setUser(u2);
    c2=credenzialiService.saveCredenziali(c2);
    assertEquals(c2.getUser().getName(), "Vladyslav");
    
    
    Project p1= new Project("TestP1");    
    p1.setOwner(u1);
    p1=projectService.salvaProgetto(p1);
    assertEquals(p1.getOwner(), u1);
    assertEquals(p1.getName(), "TestP1");
    
    Project p2= new Project("TestP2");    
    p2.setOwner(u1);
    p2=projectService.salvaProgetto(p2);
    assertEquals(p2.getOwner(), u1);
    assertEquals(p2.getName(), "TestP2");
    
    p1=projectService.condiviProgetto(p1, u2);
    List<Project> projects =projectRepository.findByOwner(u1);
    assertEquals(projects.size(), 2);
    assertEquals(projects.get(0), p1);
    assertEquals(projects.get(1), p2);
    
    List<Project> projectsVisU2 = projectRepository.findByMembers(u2);
    assertEquals(projectsVisU2.size(), 1);
    assertEquals(projectsVisU2.get(0), p1);
    
    List<User> p1Membri = userRepository.findByVisibleProjects(p1);
    assertEquals(p1Membri.size(), 1);
    assertEquals(p1Membri.get(0), u2);
    
   
  }

}