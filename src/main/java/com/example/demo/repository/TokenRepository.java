package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByToken(Token token);

	@Modifying
	@Query("DELETE FROM Token t WHERE t.token = :token")
	void deleteByToken(@Param("token") String token);
		
	@Query("SELECT t.ruolo FROM Token t WHERE t.token = :token")
	String findRuoloByToken(@Param("token") String token);
	
	@Query("SELECT t.personaleClinicaId FROM Token t WHERE t.token = :token")
	long findPersonaleClinicaByToken(@Param("token") String token);
	}
