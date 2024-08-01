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

import com.example.demo.exception.OggettoGiaPresenteException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DisponibilitaMedici;
import com.example.demo.model.Paziente;
import com.example.demo.model.Prenotazione;
import com.example.demo.repository.DisponibilitaMediciRepository;
import com.example.demo.repository.PazienteRepository;
import com.example.demo.repository.PrenotazioneRepository;

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

	// stampa di tutte le prenotazioni
	@GetMapping
	public List<Prenotazione> getAllPrenotazioni() {
		return prenotazioneRepository.findAll();
	}

	// stampa di tutte le prenotazioni attive

	@GetMapping("/searchActiveAppointment")
	public List<Prenotazione> getAllActivePrenotazioni() {
		return prenotazioneRepository.findByActive(true);
	}

	// stampa di tutte le prenotazioni chiuse

	@GetMapping("/searchNotActiveAppointment")
	public List<Prenotazione> getAllNotActivePrenotazioni() {
		return prenotazioneRepository.findByActive(false);
	}

	// stampa di una prenotazione per id

	@GetMapping("/{id}")
	public Prenotazione searchById(@PathVariable Long id) {
		return prenotazioneRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id prenotazione non trovato"));
	}

	// disattiva prenotazione

	@PutMapping("/disattivaPrenotazione/{id}")
	public Prenotazione disattivaPrenotazioneById(@PathVariable Long id) {
		Prenotazione prenotazione = prenotazioneRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
		prenotazione.setActive(false);
		return prenotazioneRepository.save(prenotazione);
	}

	// modifica penotazione tramite id

	@PutMapping("/modifyAppointmentById/{id}")
	public Prenotazione updateOraEDataPrenotazionebyId(@PathVariable Long id, @RequestParam Long nuovaDispId) {
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
	}

	// creazione di una prenotazione

	@PostMapping
	public Prenotazione createPrenotazione(@RequestBody Prenotazione prenotazione) {
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
	}

	// elimina prenotazione
	@DeleteMapping("/{id}")
	public void deletePrenotazioneById(@PathVariable Long id) {
		prenotazioneRepository.deleteById(id);
	}

	// elimina prenotazioni non attive
	@DeleteMapping("/deleteByNotActiveAppointments")
	public void deleteNotActivePrenotazioni() {
		prenotazioneRepository.deleteByNotActive();
	}
}
