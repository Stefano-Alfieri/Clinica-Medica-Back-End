package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.DisponibilitaMedici;

public interface DisponibilitaMediciRepository extends JpaRepository<DisponibilitaMedici, Long> {
	List<DisponibilitaMedici> findByMedico_Id(Long medico_Id);
}
