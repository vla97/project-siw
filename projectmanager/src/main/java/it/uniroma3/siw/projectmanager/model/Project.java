package it.uniroma3.siw.projectmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;



@Entity
@Table(name = "projects")
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 100)
	private String name;
	@Column(updatable = false, nullable = false)
	private LocalDateTime dataCreazione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;
	@ManyToMany
	private List<User> members;
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Tag> tagProgetti;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="project")
	private List<Task> tasks;
	
	

	//COSTRUTTORI
	public Project() {
		this.members = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.tagProgetti = new ArrayList<>();
	}
	public Project(Long id, String name, User owner) {
		this();
		this.id = id;
		this.name = name;
		this.owner = owner;
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


	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
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
	
	public void addMember(User user) {
		this.members.add(user);
	}
	

	public void removeTask(Task task) {
		this.tasks.remove(task);
	}
	
	public List<Tag> getTagProgetti() {
		return tagProgetti;
	}

	public void setTagProgetti(List<Tag> tagProgetti) {
		this.tagProgetti = tagProgetti;
	}
	
	public void addTag(Tag tag) {
		this.tagProgetti.add(tag);
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", dataCreazione=" + dataCreazione + ", owner=" + owner
				+ ", members=" + members + ", tagProgetti=" + tagProgetti + ", tasks=" + tasks + "]";
	}
	
	

}
