package it.uniroma3.siw.projectmanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private String colore;
	
	private String descrizione;
	
	@ManyToMany(mappedBy="tagAssociati")
	private List<Task> taskAssociati;
	@ManyToOne
	private Project projectOwner;
	//COSTRUTTORI
	

	public Tag() {}
	
	public Tag(String nome, String colore, String descrizione) {
		this();
		this.nome = nome;
		this.colore = colore;
		this.descrizione = descrizione;
	}
	
	//GETTERS AND SETTERS

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

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Project getProjectOwner() {
		return projectOwner;
	}
	
	public void setProjectOwner(Project projectOwner) {
		this.projectOwner = projectOwner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Tag other = (Tag) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public List<Task> getTaskAssociati() {
		return taskAssociati;
	}

	public void setTaskAssociati(List<Task> taskAssociati) {
		this.taskAssociati = taskAssociati;
	}

	@Override
	public String toString() {
		return nome + ", " + colore + ", " + descrizione;
	}

	
}
