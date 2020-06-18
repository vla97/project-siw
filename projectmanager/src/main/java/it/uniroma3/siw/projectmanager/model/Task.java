package it.uniroma3.siw.projectmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String nome;

	@Column
	private String descrizione;

	@ManyToMany
	private List<User> members;

	@Column
	private String isCompleto = "Non completo";

	@Column
	private LocalDateTime dataCreazione;

	@ManyToMany
	private List<Tag> tagAssociati;
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List<Commento> commenti; 

	@ManyToOne
	private Project project;

	// COSTRUTTORI

	public Task() {
		this.tagAssociati = new ArrayList<>();
	}

	public Task(String nome, String descrizione) {
		this();
		this.nome = nome;
		this.descrizione = descrizione;
		

	}

	@PrePersist
	protected void onPersist() {
		this.dataCreazione = LocalDateTime.now();
	}

	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<Tag> getTagAssociati() {
		return tagAssociati;
	}

	public void setTagAssociati(List<Tag> tagAssociati) {
		this.tagAssociati = tagAssociati;
	}

	public String getIsCompleto() {
		return isCompleto;
	}

	public void setIsCompleto(String isCompleto) {
		this.isCompleto = isCompleto;
	}

	public void aggiungiTag(Tag tag) {
		this.tagAssociati.add(tag);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
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
	public void removeMember(User user) {
		this.members.remove(user);
	}
	
	public boolean hasMember(User user) {
		return members.contains(user);
	}
	
	public void addCommento(Commento commento) {
		this.commenti.add(commento);
	}

	// HASHCODE

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((isCompleto == null) ? 0 : isCompleto.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	// EQUALS

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (isCompleto == null) {
			if (other.isCompleto != null)
				return false;
		} else if (!isCompleto.equals(other.isCompleto))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	// TOSTRING

	@Override
	public String toString() {
		return nome + " - " + descrizione + " - " + commenti + " - " + isCompleto;
	}

}
