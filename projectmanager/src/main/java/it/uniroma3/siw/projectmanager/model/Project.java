package it.uniroma3.siw.projectmanager.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 10)
	private String name;
	@Column(updatable = false, nullable = false)
	private LocalDateTime creationTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;
	@ManyToMany
	private List<User> members;
	
	
	public Project() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDateTime getCreationTime() {
		return creationTime;
	}


	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}


	public User getOwner() {
		return owner;
	}


	public void setOwner(User owner) {
		this.owner = owner;
	}


	public List<User> getMembers() {
		return members;
	}


	public void setMembers(List<User> members) {
		this.members = members;
	}
	
	

}
