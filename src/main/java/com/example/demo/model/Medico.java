package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Medico {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String cognome;
	private String specializzazione;
	private String email;
	private String password;
	private String telefono;
	
	@OneToMany(mappedBy = "medico")
    private Set<Prenotazione> prenotazioni;
	
	@OneToMany(mappedBy ="medico")
	private Set<DisponibilitaMedici> disponibilitàMedici;
	
	
	public Set<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(Set<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
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
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getSpecializzazione() {
		return specializzazione;
	}
	public void setSpecializzazione(String specializzazione) {
		this.specializzazione = specializzazione;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
