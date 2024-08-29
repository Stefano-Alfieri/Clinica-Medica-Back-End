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
import com.example.demo.model.Paziente;
import com.example.demo.model.Token;
import com.example.demo.repository.PazienteRepository;
import com.example.demo.service.TokenService;

@CrossOrigin
@RestController
@RequestMapping("/pazienti")
public class PazienteController {
	@Autowired
	private PazienteRepository pazienteRepository;
	@Autowired
	private TokenService tokenService;

	// stampa di tutti i pazienti
	@GetMapping
	public List<Paziente> getAllPazienti(@RequestHeader("Authorization") Token token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			return pazienteRepository.findAll();
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca di un paziente per id
	@GetMapping("/{id}")
	public Paziente getPazienteById(@RequestHeader("Authorization") Token token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			return pazienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca di un paziente per cognome
	@GetMapping("/SearchByCognome")
	public List<Paziente> getPazienteByCognome(@RequestHeader("Authorization") Token token,
			@RequestParam String cognome) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			return pazienteRepository.findByCognome(cognome);
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca di un paziente per nome e cognome
	@GetMapping("/searchByNomeCognome")
	public List<Paziente> getPazienteByNomeCognome(@RequestHeader("Authorization") Token token,
			@RequestParam String nome, @RequestParam String cognome) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			return pazienteRepository.findByNomeAndCognome(nome, cognome);
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca di un paziente per email
	@GetMapping("/SearchByEmail")
	public Paziente getPazienteByEmail(@RequestHeader("Authorization") Token token, @RequestParam String email) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			return pazienteRepository.findByEmail(email);
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca di un paziente per telefono
	@GetMapping("/SearchByTelefono")
	public Paziente getPazienteByTelefono(@RequestHeader("Authorization") Token token, @RequestParam String telefono) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			return pazienteRepository.findByTelefono(telefono);
		} else {
			throw new UnauthorizedException();
		}
	}

	// creazione di un paziente
	@PostMapping
	public Paziente createPaziente(@RequestHeader("Authorization") Token token, @RequestBody Paziente paziente) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return pazienteRepository.save(paziente);
		} else {
			throw new UnauthorizedException();
		}
	}

	// eliminazione di un paziente
	@DeleteMapping("/{id}")
	public void deletePaziente(@RequestHeader("Authorization") Token token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			pazienteRepository.deleteById(id);
		} else {
			throw new UnauthorizedException();
		}
	}

	// modifica di un paziente
	@PutMapping("/{id}")
	public Paziente updatePaziente(@RequestHeader("Authorization") Token token, @PathVariable Long id,
			@RequestBody Paziente pazienteDett) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Paziente paziente = pazienteRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
			paziente.setNome(pazienteDett.getNome());
			paziente.setCognome(pazienteDett.getCognome());
			paziente.setEmail(pazienteDett.getEmail());
			paziente.setTelefono(pazienteDett.getTelefono());
			return pazienteRepository.save(paziente);
		} else {
			throw new UnauthorizedException();
		}
	}

}
