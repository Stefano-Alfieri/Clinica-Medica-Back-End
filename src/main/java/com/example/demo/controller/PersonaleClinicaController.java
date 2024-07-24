package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PersonaleClinica;
import com.example.demo.repository.PersonaleClinicaRepository;

@CrossOrigin
@RestController
@RequestMapping("/personale")
public class PersonaleClinicaController {
	
	@Autowired
	private PersonaleClinicaRepository personaleClinicaRepository;
	
	//Stampa di tutto il personale della clinica
	@GetMapping
	public List<PersonaleClinica> getAllPersonaleClinica(){
		return personaleClinicaRepository.findAll();
	}
	
	//ricerca personale per id
	@GetMapping("/{id}")
	public PersonaleClinica getPersonaleClinicaById(@PathVariable Long id) {
		return personaleClinicaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id non trovato"));
	}
	
	//ricerca di personale per ruolo
	@GetMapping("/searchByRole")
	public List<PersonaleClinica> getPersonaleClinicaByRole(@RequestParam String role){
		return personaleClinicaRepository.findByRole(role);
	}
	
	//creazione personale clinica
	@PostMapping
	public PersonaleClinica createPersonaleClinica(@RequestBody PersonaleClinica personaleClinica) {
		return personaleClinicaRepository.save(personaleClinica);
	}
	
	//eliminazione personale clinica
	@DeleteMapping("/{id}")
	public void deletePersonaleClinica(@PathVariable Long id) {
		personaleClinicaRepository.deleteById(id);
	}
	
	//modifica personale clinica
	@PutMapping("/{id}")
	public PersonaleClinica updatePersonaleClinica(@PathVariable Long id, @RequestBody PersonaleClinica personaleClinicaDett) {
		PersonaleClinica personaleClinica=personaleClinicaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id non trovato"));
		personaleClinica.setNome(personaleClinicaDett.getNome());
		personaleClinica.setCognome(personaleClinicaDett.getCognome());
		personaleClinica.setEmail(personaleClinicaDett.getEmail());
		personaleClinica.setRole(personaleClinicaDett.getRole());
		return personaleClinicaRepository.save(personaleClinica);
	}
	
}
