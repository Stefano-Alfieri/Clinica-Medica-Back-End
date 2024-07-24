package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Notifica;

public interface NotificaRepository extends JpaRepository<Notifica, Long> {
	List<Notifica> findByCreatedDate(LocalDate createdDate);
	List<Notifica> findByOraMessaggio(LocalTime oraMessaggio);
	List<Notifica> findByPaziente_id(Long paziente_id);
	@Modifying
	@Query("DELETE FROM Notifica n WHERE n.letta = true AND n.paziente.id = :paziente_id")
	void deleteByLettaAndPazienteId( Long paziente_id);

}
