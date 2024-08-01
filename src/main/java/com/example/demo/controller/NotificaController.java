package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Notifica;
import com.example.demo.model.Paziente;
import com.example.demo.repository.NotificaRepository;
import com.example.demo.repository.PazienteRepository;

import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/notifiche")
public class NotificaController {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Autowired
	private NotificaRepository notificaRepository;

	@Autowired
	private PazienteRepository pazienteRepository;

	// stampa di tutte le notifiche
	@GetMapping
	public List<Notifica> getAllNotifiche() {
		return notificaRepository.findAll();
	}

	// ricerca notifica per id
	@GetMapping("/{id}")
	public Notifica getNotificaById(@PathVariable Long id) {
		return notificaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
	}

	// ricerca notifica per Data
	@GetMapping("/searchByData")
	public List<Notifica> getNotificaByData(@RequestParam LocalDate createdDate) {
		return notificaRepository.findByCreatedDate(createdDate);
	}

	// ricerca notifica per Orario
	@GetMapping("/searchByOrario")
	public List<Notifica> getNotificaByOrario(@RequestParam LocalTime oraMessaggio) {
		return notificaRepository.findByOraMessaggio(oraMessaggio);
	}

	// crea notifica
	@PostMapping
	public Notifica createNotifica(@RequestBody Notifica notifica) {
		return notificaRepository.save(notifica);
	}

	// eliminazione notifica
	@DeleteMapping("/{id}")
	public void deleteNotifica(@PathVariable Long id) {
		notificaRepository.deleteById(id);
	}

	// eliminazione notifica letta per id del paziente
	@Transactional
	@DeleteMapping("/deleteNotificheLetteByPazienteId")
	public void deleteNotificheLetteByPazienteId(@RequestParam Long paziente_id) {
		Paziente paziente = pazienteRepository.findById(paziente_id)
				.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
		notificaRepository.deleteByLettaAndPazienteId(paziente.getId());
	}

	// ricerca notifiche per orario , data e id del paziente
	@GetMapping("/searchNotificheByDataOrarioAndPazienteId")
	public List<Notifica> getAllNotificheByDataAndOrarioAndPazienteId(@RequestParam Long paziente_id,
			@RequestParam LocalTime oraMessaggio, @RequestParam LocalDate createdDate) {
		Paziente paziente = pazienteRepository.findById(paziente_id)
				.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
		List<Notifica> notificaUtente = notificaRepository.findByPaziente_id(paziente.getId());
		List<Notifica> notificheOraAndData = notificaUtente.stream()
				.filter(notifica -> notifica.getCreatedDate().equals(createdDate)
						&& notifica.getOraMessaggio().equals(oraMessaggio))
				.collect(Collectors.toList());
		return notificheOraAndData;
	}

	// ricerca notifiche per data e id del paziente
	@GetMapping("/searchNotificheByDataAndPazienteId")
	public List<Notifica> getAllNotificheByDataAndPazienteId(@RequestParam Long paziente_id,
			@RequestParam LocalDate createdDate) {
		Paziente paziente = pazienteRepository.findById(paziente_id)
				.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
		List<Notifica> notificaUtente = notificaRepository.findByPaziente_id(paziente.getId());
		List<Notifica> notificheData = notificaUtente.stream()
				.filter(notifica -> notifica.getCreatedDate().equals(createdDate)).collect(Collectors.toList());
		return notificheData;
	}

	// ricerca notifiche non lette per id pazietne
	@GetMapping("/searchNotReadByPazienteId")
	public List<Notifica> getAllNotificheNonLetteByPazienteId(@RequestParam Long paziente_id) {
		Paziente paziente = pazienteRepository.findById(paziente_id)
				.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
		List<Notifica> notificaUtente = notificaRepository.findByPaziente_id(paziente.getId()).stream()
				.filter(nonLette -> nonLette.isLetta() == false).collect(Collectors.toList());
		return notificaUtente;
	}

}
