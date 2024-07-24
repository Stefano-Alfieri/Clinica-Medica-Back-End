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
import com.example.demo.model.Paziente;
import com.example.demo.repository.PazienteRepository;

@CrossOrigin
@RestController
@RequestMapping("/pazienti")
public class PazienteController {
	@Autowired
	private PazienteRepository pazienteRepository;

	// stampa di tutti i pazienti
	@GetMapping
	public List<Paziente> getAllPazienti() {
		return pazienteRepository.findAll();
	}

	// ricerca di un paziente per id
	@GetMapping("/{id}")
	public Paziente getPazienteById(@PathVariable Long id) {
		return pazienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
	}

	// ricerca di un paziente per cognome
	@GetMapping("/SearchByCognome")
	public List<Paziente> getPazienteByCognome(@RequestParam String cognome) {
		return pazienteRepository.findByCognome(cognome);
	}

	// ricerca di un paziente per nome e cognome
	@GetMapping("/searchByNomeCognome")
	public List<Paziente> getPazienteByNomeCognome(@RequestParam String nome, @RequestParam String cognome) {
		return pazienteRepository.findByNomeAndCognome(nome, cognome);
	}

	// ricerca di un paziente per email
	@GetMapping("/SearchByEmail")
	public Paziente getPazienteByEmail(@RequestParam String email) {
		return pazienteRepository.findByEmail(email);
	}

	// ricerca di un paziente per telefono
	@GetMapping("/SearchByTelefono")
	public Paziente getPazienteByTelefono(@RequestParam String telefono) {
		return pazienteRepository.findByTelefono(telefono);
	}

	// creazione di un paziente
	@PostMapping
	public Paziente createPaziente(@RequestBody Paziente paziente) {
		return pazienteRepository.save(paziente);
	}

	// eliminazione di un paziente
	@DeleteMapping("/{id}")
	public void deletePaziente(@PathVariable Long id) {
		pazienteRepository.deleteById(id);
	}

	// modifica di un paziente
	@PutMapping("/{id}")
	public Paziente updatePaziente(@PathVariable Long id, @RequestBody Paziente pazienteDett) {
		Paziente paziente = pazienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
		paziente.setNome(pazienteDett.getNome());
		paziente.setCognome(pazienteDett.getCognome());
		paziente.setEmail(pazienteDett.getEmail());
		paziente.setTelefono(pazienteDett.getTelefono());
		return pazienteRepository.save(paziente);
	}

}
