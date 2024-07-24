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
public class DisponibilitaMedici {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
	
	private LocalDate dataDisponibilita;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public LocalDate getDataDisponibilita() {
		return dataDisponibilita;
	}
	public void setDataDisponibilita(LocalDate dataDisponibilita) {
		this.dataDisponibilita = dataDisponibilita;
	}
	public LocalTime getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	public LocalTime getOraFine() {
		return oraFine;
	}
	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}
	
	
	
}
