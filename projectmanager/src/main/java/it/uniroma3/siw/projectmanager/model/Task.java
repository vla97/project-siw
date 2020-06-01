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
	

}
