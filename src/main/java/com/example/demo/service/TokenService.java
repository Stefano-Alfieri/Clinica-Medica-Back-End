package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.repository.TokenRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	public Token createTokenPersonaleClinica(Long personaleClinicaId) {
		Token token = new Token();
		token.setPersonaleClinicaId(personaleClinicaId);
		token.setToken(generateToken());
		token.setCreatedDate(new Date());
		token.setRuolo("admin");
		return tokenRepository.save(token);
	}

	public Token createTokenPaziente(Long pazienteId) {
		Token token = new Token();
		token.setPazienteId(pazienteId);
		token.setToken(generateToken());
		token.setCreatedDate(new Date());
		token.setRuolo("paziente");
		return tokenRepository.save(token);
	}

	public Token createTokenMedico(Long medicoId) {
		Token token = new Token();
		token.setMedicoId(medicoId);
		token.setToken(generateToken());
		token.setCreatedDate(new Date());
		token.setRuolo("medico");
		return tokenRepository.save(token);
	}

	private String generateToken() {
		return java.util.UUID.randomUUID().toString();
	}

	public Token findByToken(Token token) {
		return tokenRepository.findByToken(token);
	}

	public void deleteByToken(String token) {
		tokenRepository.deleteByToken(token);
	}
	
	public String getRuoloByToken(String token) {
		return tokenRepository.findRuoloByToken(token);
	}
	public long getAdminIdByToken(String token) {
		return tokenRepository.findPersonaleClinicaByToken(token);
	}
	
	public long getPazienteIdByToken(String token) {
		return tokenRepository.findPazienteIdByToken(token);
	}
	
}
