package com.example.demo.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Paziente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;
	private String nome;
	private String cognome;
	private String telefono;
	
	@JsonIgnore
	@OneToMany(mappedBy = "paziente")
	private Set<Prenotazione> prenotazioni;

	@OneToMany(mappedBy = "paziente")
	private Set<Notifica> notifiche;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

}
