package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.demo.model.Medico;
import com.example.demo.repository.DisponibilitaMediciRepository;
import com.example.demo.repository.MedicoRepository;

@CrossOrigin
@RestController
@RequestMapping("/disponibilita")
public class DisponibilitaMediciController {

	@Autowired
	private DisponibilitaMediciRepository disponibilitaMediciRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	// stampa di tutte le dispopnibilità
	@GetMapping
	public List<DisponibilitaMedici> getAllDisponibilitaMedici() {
		return disponibilitaMediciRepository.findAll();
	}

	// ricerca di disponibilità per id
	@GetMapping("/{id}")
	public DisponibilitaMedici getAllDisponibilitaMediciById(@PathVariable Long id) {
		return disponibilitaMediciRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("id non trovato"));
	}

	// ricerca disponibilita per Cognome medico
	@GetMapping("/searchDisponibilitaByMedicoCognome")
	public List<DisponibilitaMedici> getAllDisponibilitaMediciByMedicoCognome(@RequestParam String cognome) {
		List<Medico> medici = medicoRepository.findByCognome(cognome);
		if (!medici.isEmpty()) {
			List<DisponibilitaMedici> listaDisponibilita = new ArrayList<>();
			for (Medico medico : medici) {
				List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
				listaDisponibilita.addAll(disponibilita);
			}
			return listaDisponibilita;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile col cognome indicato");
		}
	}

	// ricerca disponibilita per nome e Cognome medico
	@GetMapping("/searchDisponibilitaByMedicoNomeCognome")
	public List<DisponibilitaMedici> getAllDisponibilitaMediciByMedicoNomeCognome(@RequestParam String nome,
			@RequestParam String cognome) {
		List<Medico> medici = medicoRepository.findByNomeAndCognome(nome, cognome);
		if (!medici.isEmpty()) {
			List<DisponibilitaMedici> listaDisponibilita = new ArrayList<>();
			for (Medico medico : medici) {
				List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
				listaDisponibilita.addAll(disponibilita);
			}
			return listaDisponibilita;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile col nome e cognome indicato");
		}
	}

	// ricerca disponibilita per id medico
	@GetMapping("/searchDisponibilitaByMedicoId")
	public List<DisponibilitaMedici> getAllDisponibilitaMediciByMedicoId(@RequestParam Long medico_id) {
		Medico medico = medicoRepository.findById(medico_id)
				.orElseThrow(() -> new ResourceNotFoundException("id medico non trovato"));
		List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
		if (!disponibilita.isEmpty()) {
			return disponibilita;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile con l'id passato");
		}
	}

	// ricerca disponibilita per id medico attive
	@GetMapping("/searchDisponibilitaAttiveByMedicoId")
	public List<DisponibilitaMedici> getAllActiveDisponibilitaMediciByMedicoId(@RequestParam Long medico_id) {
		Medico medico = medicoRepository.findById(medico_id)
				.orElseThrow(() -> new ResourceNotFoundException("id medico non trovato"));
		List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
		if (!disponibilita.isEmpty()) {
			List<DisponibilitaMedici> disponibilitaAtt = new ArrayList<>();
			for (DisponibilitaMedici disp : disponibilita) {
				if (disp.isStatus()) {
					disponibilitaAtt.add(disp);
				}
			}
			return disponibilitaAtt;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile con l'id passato");
		}
	}

	// ricerca disponibilita per id medico non attive
	@GetMapping("/searchDisponibilitaNonAttiveByMedicoId")
	public List<DisponibilitaMedici> getAllNotActiveDisponibilitaMediciByMedicoId(@RequestParam Long medico_id) {
		Medico medico = medicoRepository.findById(medico_id)
				.orElseThrow(() -> new ResourceNotFoundException("id medico non trovato"));
		List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
		if (!disponibilita.isEmpty()) {
			List<DisponibilitaMedici> disponibilitaNonAtt = new ArrayList<>();
			for (DisponibilitaMedici disp : disponibilita) {
				if (!disp.isStatus()) {
					disponibilitaNonAtt.add(disp);
				}
			}
			return disponibilitaNonAtt;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile con l'id passato");
		}
	}

	// ricerca disponibilita per email
	@GetMapping("/searchDisponibilitaByMedicoEmail")
	public List<DisponibilitaMedici> getAllDisponibilitaMediciByMedicoEmail(@RequestParam String email) {
		Medico medico = medicoRepository.findByEmail(email);
		List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
		if (!disponibilita.isEmpty()) {
			return disponibilita;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile con la mail passata");
		}
	}

	// ricerca disponibilita per telefono
	@GetMapping("/searchDisponibilitaByMedicoTelefono")
	public List<DisponibilitaMedici> getAllDisponibilitaMediciByMedicoTelefono(@RequestParam String telefono) {
		Medico medico = medicoRepository.findByTelefono(telefono);
		List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
		if (!disponibilita.isEmpty()) {
			return disponibilita;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile con il numero di telefono passato");
		}
	}

	// ricerca disponibilita per specializzazione
	@GetMapping("/searchDisponibilitaBySpecializzazione")
	public List<DisponibilitaMedici> getAllDisponibilitaMediciBySpecializzazione(
			@RequestParam String specializzazione) {
		List<Medico> medici = medicoRepository.findBySpecializzazione(specializzazione);
		if (!medici.isEmpty()) {
			List<DisponibilitaMedici> listaDisponibilita = new ArrayList<>();
			for (Medico medico : medici) {
				List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
				listaDisponibilita.addAll(disponibilita);
			}
			return listaDisponibilita;
		} else {
			throw new ResourceNotFoundException("nessun medico disponibile con questa specializzazione");
		}
	}

	// crea disponibilità medico per id
	@PostMapping
	public DisponibilitaMedici createDisponibilitaByMedicoId(@RequestParam Long medico_id,
			@RequestBody DisponibilitaMedici disponibilitaMedici) {
		Medico medico = medicoRepository.findById(medico_id)
				.orElseThrow(() -> new ResourceNotFoundException("id medico non trovato"));
		List<DisponibilitaMedici> disponibilita = disponibilitaMediciRepository.findByMedico_Id(medico.getId());
		if (disponibilita.isEmpty() || !disponibilita.contains(disponibilitaMedici)) {
			return disponibilitaMediciRepository.save(disponibilitaMedici);
		} else if (disponibilita.contains(disponibilitaMedici)) {
			throw new OggettoGiaPresenteException("Orario già disponibile");
		} else {
			throw new ResourceNotFoundException("risorsa non trovata");
		}
	}

	// modifica disponibilita medico
	@PutMapping("/{id}")
	public DisponibilitaMedici updateDisponibilitaMedici(@PathVariable Long id,
			@RequestBody DisponibilitaMedici disponibilitaMediciDett) {
		DisponibilitaMedici disponibilitaMedici = disponibilitaMediciRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Disponibilità non trovata"));
		disponibilitaMedici.setDataDisp(disponibilitaMediciDett.getDataDisp());
		disponibilitaMedici.setOraDisp(disponibilitaMediciDett.getOraDisp());
		return disponibilitaMediciRepository.save(disponibilitaMedici);
	}

	// disattiva disponibilita
	@PutMapping("/disattivaDisponibilita/{id}")
	public DisponibilitaMedici disattivaDisponibilitaMedici(@PathVariable Long id) {
		DisponibilitaMedici disponibilitaMedici = disponibilitaMediciRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Disponibilità non trovata"));
		disponibilitaMedici.setStatus(false);
		return disponibilitaMediciRepository.save(disponibilitaMedici);
	}

	// attiva disponibilita
	@PutMapping("/attivaDisponibilita/{id}")
	public DisponibilitaMedici attivaDisponibilitaMedici(@PathVariable Long id) {
		DisponibilitaMedici disponibilitaMedici = disponibilitaMediciRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Disponibilità non trovata"));
		disponibilitaMedici.setStatus(true);
		return disponibilitaMediciRepository.save(disponibilitaMedici);
	}
}
