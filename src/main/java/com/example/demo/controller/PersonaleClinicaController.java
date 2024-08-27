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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.PersonaleClinica;
import com.example.demo.model.Token;
import com.example.demo.repository.PersonaleClinicaRepository;
import com.example.demo.service.TokenService;

@CrossOrigin
@RestController
@RequestMapping("/personale")
public class PersonaleClinicaController {

	@Autowired
	private PersonaleClinicaRepository personaleClinicaRepository;

	@Autowired
	private TokenService tokenService;

	// Stampa di tutto il personale della clinica
	@GetMapping
	public List<PersonaleClinica> getAllPersonaleClinica() {
		return personaleClinicaRepository.findAll();
	}

	// ricerca personale per id
	@GetMapping("/{id}")
	public PersonaleClinica getPersonaleClinicaById(@RequestHeader("Authorization") String token,
			@PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			return personaleClinicaRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca personale per mail
	@GetMapping("/searchByEmail")
	public PersonaleClinica getPersonaleClinicaByEmail(@RequestHeader("Authorization") String token,
			@RequestParam String email) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			return personaleClinicaRepository.findByEmail(email);
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca di personale per ruolo
	@GetMapping("/searchByRole")
	public List<PersonaleClinica> getPersonaleClinicaByRole(@RequestHeader("Authorization") String token,
			@RequestParam String role) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			return personaleClinicaRepository.findByRole(role);
		} else {
			throw new UnauthorizedException();
		}
	}

	// creazione personale clinica
	@PostMapping
	public PersonaleClinica createPersonaleClinica(@RequestHeader("Authorization") String token,
			@RequestBody PersonaleClinica personaleClinica) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			return personaleClinicaRepository.save(personaleClinica);
		} else {
			throw new UnauthorizedException();
		}
	}

	// eliminazione personale clinica
	@DeleteMapping("/{id}")
	public void deletePersonaleClinica(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			personaleClinicaRepository.deleteById(id);
		} else {
			throw new UnauthorizedException();
		}
	}

	// modifica personale clinica
	@PutMapping("/{id}")
	public PersonaleClinica updatePersonaleClinica(@RequestHeader("Authorization") String token, @PathVariable Long id,
			@RequestBody PersonaleClinica personaleClinicaDett) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			PersonaleClinica personaleClinica = personaleClinicaRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
			personaleClinica.setNome(personaleClinicaDett.getNome());
			personaleClinica.setCognome(personaleClinicaDett.getCognome());
			personaleClinica.setEmail(personaleClinicaDett.getEmail());
			personaleClinica.setRole(personaleClinicaDett.getRole());
			return personaleClinicaRepository.save(personaleClinica);
		} else {
			throw new UnauthorizedException();
		}
	}

}
