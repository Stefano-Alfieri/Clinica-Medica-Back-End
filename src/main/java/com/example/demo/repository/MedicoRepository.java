package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{
	Medico findByCognome(String cognome);
	Medico findBySpecializzazione(String specializzazione);
}
