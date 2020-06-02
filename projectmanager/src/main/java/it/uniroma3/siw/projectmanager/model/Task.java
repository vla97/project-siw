package it.uniroma3.siw.projectmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	
	private String descrizione;
	
	private Boolean isCompleto;
	
	@Column(nullable = false, updatable=false)
	private LocalDateTime dataCreazione;
	private String commento;

	@ManyToMany
	private List<Tag> tagAssociati;
	
	//COSTRUTTORI
	
	public Task() {
		this.tagAssociati = new ArrayList<>();
	}
	
	
	public Task(String nome, String descrizione, String commento) {
		this();
		this.nome = nome;
		this.descrizione = descrizione;
		this.commento = commento;
		this.isCompleto = false;
		
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

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public List<Tag> getTagAssociati() {
		return tagAssociati;
	}

	public void setTagAssociati(List<Tag> tagAssociati) {
		this.tagAssociati = tagAssociati;
	}


	public Boolean getIsCompleto() {
		return isCompleto;
	}


	public void setIsCompleto(Boolean isCompleto) {
		this.isCompleto = isCompleto;
	}
	
	public void aggiungiTag(Tag tag) {
		this.tagAssociati.add(tag);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((isCompleto == null) ? 0 : isCompleto.hashCode());
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
	

}
