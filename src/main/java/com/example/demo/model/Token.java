package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Token {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	private Long personaleClinicaId;
	private Long pazienteId;
	private Long medicoId;
	private Date createdDate;
	private String ruolo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getPersonaleClinicaId() {
		return personaleClinicaId;
	}
	public void setPersonaleClinicaId(Long personaleClinicaId) {
		this.personaleClinicaId = personaleClinicaId;
	}
	public Long getPazienteId() {
		return pazienteId;
	}
	public void setPazienteId(Long pazienteId) {
		this.pazienteId = pazienteId;
	}
	public Long getMedicoId() {
		return medicoId;
	}
	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	
	
}
