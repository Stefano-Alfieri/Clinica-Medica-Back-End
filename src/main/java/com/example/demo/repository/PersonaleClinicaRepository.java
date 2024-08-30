package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.PersonaleClinica;

public interface PersonaleClinicaRepository extends JpaRepository<PersonaleClinica, Long> {
	List<PersonaleClinica> findByRole(String role);
	PersonaleClinica findByEmail(String email);
	
	@Query("SELECT COUNT(*) FROM PersonaleClinica")
	long count();
}
