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
import com.example.demo.model.Medico;
import com.example.demo.model.Paziente;
import com.example.demo.model.Token;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.repository.PazienteRepository;
import com.example.demo.service.TokenService;

@CrossOrigin
@RestController
@RequestMapping("/medici")
public class MedicoController {
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private TokenService tokenService;

	// stampa di tutti i medici
	@GetMapping
	public List<Medico> getAllMedici() {
		return medicoRepository.findAll();
	}
	
	//stampa del numero degli elementi in una tabella
	@GetMapping("/number")
	public long getNumMed() {
		return medicoRepository.count();
	}


	// ricerca di un medico per id
	@GetMapping("/{id}")
	public Medico getMedicoById(@PathVariable Long id) {
		return medicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
	}

	// ricerca di un medico per cognome
	@GetMapping("/searchByCognome")
	public List<Medico> getMedicoByCognome(@RequestParam String cognome) {
		return medicoRepository.findByCognome(cognome);
	}

	// ricerca di un medico per nome e cognome
	@GetMapping("/searchByNomeECognome")
	public List<Medico> getMedicoByNomeCognome(@RequestParam String nome, @RequestParam String cognome) {
		return medicoRepository.findByNomeAndCognome(nome, cognome);
	}

	// ricerca di un medico per email
	@GetMapping("/searchByEmail")
	public Medico getMedicoByEmail(@RequestParam String email) {
		return medicoRepository.findByEmail(email);
	}

	// ricerca di un medico per telefono
	@GetMapping("/searchByTelefono")
	public Medico getMedicoByTelefono(@RequestParam String telefono) {
		return medicoRepository.findByTelefono(telefono);
	}

	// ricerca di un medico per specializzazione
	@GetMapping("/searchBySpecializzazione")
	public List<Medico> getMedicoBySpecializzazione(@RequestParam String specializzazione) {
		return medicoRepository.findBySpecializzazione(specializzazione);
	}

	// creazione di un medico
	@PostMapping
	public Medico createMedico(@RequestBody Medico medico) {
		
			return medicoRepository.save(medico);
		
	}

	// eliminazione di un medico
	@DeleteMapping("/{id}")
	public void deleteMedico(@RequestHeader("Authorization") Token token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin")) {
			medicoRepository.deleteById(id);
		} else {
			throw new UnauthorizedException();
		}
	}

	// modifica di un medico
	@PutMapping("/{id}")
	public Medico updateMedico(@RequestHeader("Authorization") Token token, @PathVariable Long id,
			@RequestBody Medico medicoDett) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null && authToken.getRuolo().equals("admin") || authToken.getRuolo().equals("medico")) {
			Medico medico = medicoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
			medico.setNome(medicoDett.getNome());
			medico.setCognome(medicoDett.getCognome());
			medico.setEmail(medicoDett.getEmail());
			medico.setTelefono(medicoDett.getTelefono());
			medico.setSpecializzazione(medicoDett.getSpecializzazione());
			return medicoRepository.save(medico);

		} else {
			throw new UnauthorizedException();
		}
	}
}
