package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{
	List<Medico> findByCognome(String cognome);
	List<Medico> findBySpecializzazione(String specializzazione);
	Medico findByEmail(String email);
	Medico findByTelefono(String telefono);
	List<Medico> findByNomeAndCognome(String nome, String cognome);
}
