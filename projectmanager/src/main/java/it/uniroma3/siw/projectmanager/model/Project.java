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
import javax.persistence.PrePersist;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 10)
	private String name;
	@Column(updatable = false, nullable = false)
	private LocalDateTime dataCreazione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;
	@ManyToMany
	private List<User> members;
	
	//COSTRUTTORI
	public Project() {
		
	}
	
	@PrePersist
	protected void onPersist() {
		this.dataCreazione = LocalDateTime.now();
	}

	
	//GETTERS AND SETTERS
	
	
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


	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(LocalDateTime creationTime) {
		this.dataCreazione = creationTime;
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
