package it.uniroma3.siw.projectmanager.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String cognome;
	
	@Column(unique = true, nullable = false, length = 16)
	private String username;
	
	@Column(nullable = false, length = 16)
	private String password;
	
	@Column(updatable = false, nullable = false)
	private LocalDateTime dataCreazione;
	
	@OneToMany(mappedBy = "owner",
			fetch = FetchType.EAGER,
			cascade = CascadeType.REMOVE)
	private List<Project> ownedProjects;
	
	@ManyToMany(mappedBy = "members",
			fetch = FetchType.LAZY)
	private List<Project> visibleProjects;
	
	//COSTRUTTORI
	
	public User() {
		
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<Project> getOwnedProjects() {
		return ownedProjects;
	}

	public void setOwnedProjects(List<Project> ownedProjects) {
		this.ownedProjects = ownedProjects;
	}

	public List<Project> getVisibleProjects() {
		return visibleProjects;
	}

	public void setVisibleProjects(List<Project> visibleProjects) {
		this.visibleProjects = visibleProjects;
	}
	
	

}
