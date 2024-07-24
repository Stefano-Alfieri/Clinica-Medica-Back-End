package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Notifica;
import com.example.demo.model.Paziente;

@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long> {
	List<Paziente> findByCognome(String cognome);
	
	List<Paziente> findByNomeAndCognome(String nome, String cognome);

	Paziente findByEmail(String email);

	Paziente findByTelefono(String telefono);

}
