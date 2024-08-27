package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	List<Prenotazione> findByActive(boolean active);
/*	@Modifying
	@Query("DELETE FROM prenotazione p WHERE p.active = false")
	void deleteByNotActive();
	*/
}
