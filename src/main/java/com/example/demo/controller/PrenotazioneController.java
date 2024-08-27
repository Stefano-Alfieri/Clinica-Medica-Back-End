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

import com.example.demo.exception.OggettoGiaPresenteException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.DisponibilitaMedici;
import com.example.demo.model.Paziente;
import com.example.demo.model.Prenotazione;
import com.example.demo.model.Token;
import com.example.demo.repository.DisponibilitaMediciRepository;
import com.example.demo.repository.PazienteRepository;
import com.example.demo.repository.PrenotazioneRepository;
import com.example.demo.service.TokenService;

@CrossOrigin
@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
	@Autowired
	private PrenotazioneRepository prenotazioneRepository;

	@Autowired
	private PazienteRepository pazienteRepository;

	@Autowired
	private DisponibilitaMediciRepository disponibilitaMediciRepository;

	@Autowired
	private TokenService tokenService;

	// stampa di tutte le prenotazioni
	@GetMapping
	public List<Prenotazione> getAllPrenotazioni(@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return prenotazioneRepository.findAll();
		} else {
			throw new UnauthorizedException();
		}
	}

	// stampa di tutte le prenotazioni attive

	@GetMapping("/searchActiveAppointment")
	public List<Prenotazione> getAllActivePrenotazioni(@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return prenotazioneRepository.findByActive(true);
		} else {
			throw new UnauthorizedException();
		}
	}

	// stampa di tutte le prenotazioni chiuse

	@GetMapping("/searchNotActiveAppointment")
	public List<Prenotazione> getAllNotActivePrenotazioni(@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return prenotazioneRepository.findByActive(false);
		} else {
			throw new UnauthorizedException();
		}
	}

	// stampa di una prenotazione per id

	@GetMapping("/{id}")
	public Prenotazione searchById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return prenotazioneRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Id prenotazione non trovato"));
		} else {
			throw new UnauthorizedException();
		}
	}

	// disattiva prenotazione

	@PutMapping("/disattivaPrenotazione/{id}")
	public Prenotazione disattivaPrenotazioneById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Prenotazione prenotazione = prenotazioneRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
			prenotazione.setActive(false);
			return prenotazioneRepository.save(prenotazione);
		} else {
			throw new UnauthorizedException();
		}
	}

	// modifica penotazione tramite id

	@PutMapping("/modifyAppointmentById/{id}")
	public Prenotazione updateOraEDataPrenotazionebyId(@RequestHeader("Authorization") String token,
			@PathVariable Long id, @RequestParam Long nuovaDispId) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Prenotazione pren = prenotazioneRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("id prenotazione non valido"));
			DisponibilitaMedici disp = disponibilitaMediciRepository.findById(pren.getDisponibilita().getId())
					.orElseThrow(() -> new ResourceNotFoundException("id disponibilità non trovato"));
			DisponibilitaMedici disp2 = disponibilitaMediciRepository.findById(nuovaDispId)
					.orElseThrow(() -> new ResourceNotFoundException("id disponibilità non trovato"));
			if (!disp2.isStatus()) {
				throw new OggettoGiaPresenteException("orario già occupato");
			} else {
				disp2.setStatus(false);
				disp.setStatus(true);
				pren.setDisponibilita(disp2);
				return prenotazioneRepository.save(pren);
			}
		} else {
			throw new UnauthorizedException();
		}
	}

	// creazione di una prenotazione

	@PostMapping
	public Prenotazione createPrenotazione(@RequestHeader("Authorization") String token,
			@RequestBody Prenotazione prenotazione) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			// controllo se la disponibilità sia nulla
			if (disponibilitaMediciRepository.findById(prenotazione.getDisponibilita().getId()) == null) {
				throw new OggettoGiaPresenteException("Disponibilità non fornita");
			} else {
				// salvo prenotazione e la sua rispettiva disponibilità in degli oggetti

				Prenotazione pren = prenotazione;

				DisponibilitaMedici disp = disponibilitaMediciRepository.findById(pren.getDisponibilita().getId())
						.orElseThrow(() -> new ResourceNotFoundException("id disponibilità non trovato"));
				Paziente paz = pazienteRepository.findById(pren.getPaziente().getId())
						.orElseThrow(() -> new ResourceNotFoundException("id paziente non trovato"));
				// controllo se la disponbilità attiva o disattivata
				if (!disp.isStatus()) {
					throw new OggettoGiaPresenteException("orario già occupato");
				} else {
					// impopsto prenotazione come attiva
					pren.setActive(true);
					// disattivo la disponibilita
					disp.setStatus(false);
					// salvo la disponibilità e restituisco il salvataggio della prenotazione nel DB
					pren.setDisponibilita(disp);
					pren.setPaziente(paz);
					return prenotazioneRepository.save(pren);
				}
			}
		} else {
			throw new UnauthorizedException();
		}
	}

	// elimina prenotazione
	@DeleteMapping("/{id}")
	public void deletePrenotazioneById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			prenotazioneRepository.deleteById(id);
		} else {
			throw new UnauthorizedException();
		}
	}

	// elimina prenotazioni non attive
	@DeleteMapping("/deleteByNotActiveAppointments")
	public void deleteNotActivePrenotazioni(@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			prenotazioneRepository.deleteByNotActive();
		} else {
			throw new UnauthorizedException();
		}
	}
	
}
