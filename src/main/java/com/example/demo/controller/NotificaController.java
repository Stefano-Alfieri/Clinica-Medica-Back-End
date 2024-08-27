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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Notifica;
import com.example.demo.model.Paziente;
import com.example.demo.model.Token;
import com.example.demo.repository.NotificaRepository;
import com.example.demo.repository.PazienteRepository;
import com.example.demo.service.TokenService;

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

	@Autowired
	private TokenService tokenService;

	// stampa di tutte le notifiche
	@GetMapping
	public List<Notifica> getAllNotifiche(@RequestHeader("Authorization") String token) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return notificaRepository.findAll();
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca notifica per id
	@GetMapping("/{id}")
	public Notifica getNotificaById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return notificaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca notifica per Data
	@GetMapping("/searchByData")
	public List<Notifica> getNotificaByData(@RequestHeader("Authorization") String token,
			@RequestParam LocalDate createdDate) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return notificaRepository.findByCreatedDate(createdDate);
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca notifica per Orario
	@GetMapping("/searchByOrario")
	public List<Notifica> getNotificaByOrario(@RequestHeader("Authorization") String token,
			@RequestParam LocalTime oraMessaggio) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return notificaRepository.findByOraMessaggio(oraMessaggio);
		} else {
			throw new UnauthorizedException();
		}
	}

	// crea notifica
	@PostMapping
	public Notifica createNotifica(@RequestHeader("Authorization") String token, @RequestBody Notifica notifica) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			return notificaRepository.save(notifica);
		} else {
			throw new UnauthorizedException();
		}
	}

	// eliminazione notifica
	@DeleteMapping("/{id}")
	public void deleteNotifica(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			notificaRepository.deleteById(id);
		} else {
			throw new UnauthorizedException();
		}
	}

	// eliminazione notifica letta per id del paziente
	@Transactional
	@DeleteMapping("/deleteNotificheLetteByPazienteId")
	public void deleteNotificheLetteByPazienteId(@RequestHeader("Authorization") String token,
			@RequestParam Long paziente_id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Paziente paziente = pazienteRepository.findById(paziente_id)
					.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
			notificaRepository.deleteByLettaAndPazienteId(paziente.getId());
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca notifiche per orario , data e id del paziente
	@GetMapping("/searchNotificheByDataOrarioAndPazienteId")
	public List<Notifica> getAllNotificheByDataAndOrarioAndPazienteId(@RequestHeader("Authorization") String token,
			@RequestParam Long paziente_id, @RequestParam LocalTime oraMessaggio, @RequestParam LocalDate createdDate) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Paziente paziente = pazienteRepository.findById(paziente_id)
					.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
			List<Notifica> notificaUtente = notificaRepository.findByPaziente_id(paziente.getId());
			List<Notifica> notificheOraAndData = notificaUtente.stream()
					.filter(notifica -> notifica.getCreatedDate().equals(createdDate)
							&& notifica.getOraMessaggio().equals(oraMessaggio))
					.collect(Collectors.toList());
			return notificheOraAndData;
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca notifiche per data e id del paziente
	@GetMapping("/searchNotificheByDataAndPazienteId")
	public List<Notifica> getAllNotificheByDataAndPazienteId(@RequestHeader("Authorization") String token,
			@RequestParam Long paziente_id, @RequestParam LocalDate createdDate) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Paziente paziente = pazienteRepository.findById(paziente_id)
					.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
			List<Notifica> notificaUtente = notificaRepository.findByPaziente_id(paziente.getId());
			List<Notifica> notificheData = notificaUtente.stream()
					.filter(notifica -> notifica.getCreatedDate().equals(createdDate)).collect(Collectors.toList());
			return notificheData;
		} else {
			throw new UnauthorizedException();
		}
	}

	// ricerca notifiche non lette per id pazietne
	@GetMapping("/searchNotReadByPazienteId")
	public List<Notifica> getAllNotificheNonLetteByPazienteId(@RequestHeader("Authorization") String token,
			@RequestParam Long paziente_id) {
		Token authToken = tokenService.findByToken(token);
		if (authToken != null) {
			Paziente paziente = pazienteRepository.findById(paziente_id)
					.orElseThrow(() -> new ResourceNotFoundException("id paziennte non trovato"));
			List<Notifica> notificaUtente = notificaRepository.findByPaziente_id(paziente.getId()).stream()
					.filter(nonLette -> nonLette.isLetta() == false).collect(Collectors.toList());
			return notificaUtente;
		} else {
			throw new UnauthorizedException();
		}
	}

}
