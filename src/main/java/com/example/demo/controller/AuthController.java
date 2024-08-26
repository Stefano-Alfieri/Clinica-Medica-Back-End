package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Medico;
import com.example.demo.model.Paziente;
import com.example.demo.model.PersonaleClinica;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.repository.PazienteRepository;
import com.example.demo.repository.PersonaleClinicaRepository;
import com.example.demo.request.AuthRequest;
import com.example.demo.response.AuthResponse;
import com.example.demo.service.TokenService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PazienteRepository pazienteRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PersonaleClinicaRepository personaleClinicaRepository;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest authRequest) {
		Paziente paziente = pazienteRepository.findByEmail(authRequest.getEmail());
		if (paziente != null && paziente.getPassword().equals(authRequest.getPassword())) {
			String token = tokenService.createTokenPaziente(paziente.getId()).getToken();
			return new AuthResponse(token);
		} else {
			Medico medico = medicoRepository.findByEmail(authRequest.getEmail());
			if (medico != null && medico.getPassword().equals(authRequest.getPassword())) {
				String token = tokenService.createTokenMedico(medico.getId()).getToken();
				return new AuthResponse(token);
			} else {
				PersonaleClinica personaleClinica = personaleClinicaRepository.findByEmail(authRequest.getEmail());
				if (personaleClinica != null && personaleClinica.getPassword().equals(authRequest.getPassword())) {
					String token = tokenService.createTokenPersonaleClinica(personaleClinica.getId()).getToken();
					return new AuthResponse(token);

				} else {
					throw new UnauthorizedException();

				}
			}
		}
	}

	@PostMapping("/logout")
	public void logout(@RequestHeader("Authorization") String token) {
		tokenService.deleteByToken(token);
	}

}
