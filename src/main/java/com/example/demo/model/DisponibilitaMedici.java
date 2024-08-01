package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    
    @Column(columnDefinition = "boolean default true")
    private boolean status;
    private LocalDate dataDisp;
    private LocalTime oraDisp;

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
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public LocalDate getDataDisp() {
        return dataDisp;
    }
    public void setDataDisp(LocalDate dataDisp) {
        this.dataDisp = dataDisp;
    }
    public LocalTime getOraDisp() {
        return oraDisp;
    }
    public void setOraDisp(LocalTime oraDisp) {
        this.oraDisp = oraDisp;
    }
}
