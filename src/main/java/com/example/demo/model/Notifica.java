package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notifica {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name= "paziente_id",nullable= false)
	private Paziente paziente;
	
	private String messaggio;
	private LocalDate createdDate;
	private boolean letta;
	private LocalTime oraMessaggio;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Paziente getPaziente() {
		return paziente;
	}
	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public boolean isLetta() {
		return letta;
	}
	public void setLetta(boolean letta) {
		this.letta = letta;
	}
	public LocalTime getOraMessaggio() {
		return oraMessaggio;
	}
	public void setOraMessaggio(LocalTime oraMessaggio) {
		this.oraMessaggio = oraMessaggio;
	}
	
	
}
