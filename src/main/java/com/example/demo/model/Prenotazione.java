package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prenotazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    
    @ManyToOne
    @JoinColumn(name = "disponibilita_id", nullable = false)
    private DisponibilitaMedici disponibilita;
    
    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DisponibilitaMedici getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(DisponibilitaMedici disponibilita) {
        this.disponibilita = disponibilita;
    }

 
    public Paziente getPaziente() {
        return paziente;
    }
   
    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }
}
