package com.travelbuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.travelbuddy.dto.UtenteCreateDTO;
import com.travelbuddy.dto.UtenteUpdateDTO;
import com.travelbuddy.entity.Utente;
import com.travelbuddy.exception.NotFoundException;
import com.travelbuddy.exception.UserAlreadyExistsException;
import com.travelbuddy.listaenum.EnumRuolo;
import com.travelbuddy.listaenum.EnumStatus;
import com.travelbuddy.repository.UtenteRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UtenteServiceImpl implements UtenteService{
	
	//Dependency Injection 
	public final UtenteRepository utenteRepository; 
	
	public UtenteServiceImpl(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository; 
	}
	
	
	@Override
	public List<Utente> findAllUtenti() {
		return utenteRepository.findAll();
	}

	@Override
	public Utente findUtenteById(Long id) {
		//il front end mi passa un parametro nullo
		if(id == null) {
			throw new IllegalArgumentException("Id " + id + " nullo"); 
		}
		//metto un Optional perchè il metodo potrebbe non avere nessun utente collegato a quell'id
		Optional<Utente> utente = utenteRepository.findById(id); 
		return utente.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
	}

	@Override
	public Utente findUtenteByNickname(String nickname) {
		if(nickname == null || nickname.isBlank()) {
			throw new IllegalArgumentException("Errore: nickname nullo o composto da soli spazi"); 
		}
		Optional<Utente> utente = utenteRepository.findByNicknameIgnoreCase(nickname);
		return utente.orElseThrow(() -> new NotFoundException("Utente con "
				+ "nickname " + nickname + " non trovato"));
	}
	
	@Override
	public Utente findUtenteByEmail(String email) {
		if(email == null || email.isBlank()) {
			throw new IllegalArgumentException("Errore: email nulla o composta da soli spazi"); 
		}
		Optional<Utente> utente = utenteRepository.findByEmail(email);
		return utente.orElseThrow(() -> new NotFoundException("Utente con email " + email + " non"
				+ " trovato"));
	}
	
	
	@Override
	@Transactional
	public Utente replaceUtenteById(UtenteUpdateDTO utenteDTO, Long id) {
		if(utenteDTO == null) {
			throw new IllegalArgumentException("L'utente passato è nullo"); 
		}
		if(id == null) {
			throw new IllegalArgumentException("L'id passato è nullo"); 
		}
		Optional<Utente> optUtente = utenteRepository.findById(id); 
		Utente utenteEsistente = optUtente.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non"
				+ " trovato"));
		
		if(utenteDTO.getNome() == null || utenteDTO.getCognome() == null || 
				utenteDTO.getEmail() == null || (utenteDTO.getEta() < 18 || utenteDTO.getEta() == null) 
				|| utenteDTO.getNickname() == null) {
			throw new IllegalArgumentException("Tutti i campi sono obbligatori per il replace"); 
		}
		
		utenteEsistente.setNome(utenteDTO.getNome()); 
		utenteEsistente.setCognome(utenteDTO.getCognome());
		utenteEsistente.setNickname(utenteDTO.getNickname()); 
		utenteEsistente.setEta(utenteDTO.getEta());
		utenteEsistente.setEmail(utenteDTO.getEmail());
		
		return utenteRepository.save(utenteEsistente);
	}

	@Override
	@Transactional
	public Utente updateUtenteById(UtenteUpdateDTO utenteDTO, Long id) {
		
		if(utenteDTO == null) {
			throw new IllegalArgumentException("L'utente passato è nullo"); 
		}
		if(id == null) {
			throw new IllegalArgumentException("L'id passato è nullo"); 
		}
		Optional<Utente> optUtente = utenteRepository.findById(id); 
		Utente utenteEsistente = optUtente.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non"
				+ " trovato"));
		
		if(!(utenteDTO.getNome() == null || utenteDTO.getNome().isBlank())) {
			utenteEsistente.setNome(utenteDTO.getNome());
		}
		if(!(utenteDTO.getCognome() == null || utenteDTO.getCognome().isBlank())) {
			utenteEsistente.setCognome(utenteDTO.getCognome());
		}
		if(!(utenteDTO.getNickname() == null || utenteDTO.getNickname().isBlank())) {
			utenteEsistente.setNickname(utenteDTO.getNickname());
		}
		if(!(utenteDTO.getEta() == null || utenteDTO.getEta() < 18)) {
			utenteEsistente.setEta(utenteDTO.getEta());
		}
		if(!(utenteDTO.getEmail() == null || utenteDTO.getEmail().isBlank())) {
			utenteEsistente.setEmail(utenteDTO.getEmail());
		}
		
		return utenteRepository.save(utenteEsistente);
		
	}

	@Override
	@Transactional
	public Utente RegistrazioneNuovoUtente(UtenteCreateDTO utenteDTO) {
		
		if(utenteDTO == null) {
			throw new IllegalArgumentException("Dati di registrazione mancanti"); 
		}
		
		//il guest può creare un account se mette un'email o un nickname che non sono già presenti 
		if(utenteRepository.existsByEmail(utenteDTO.getEmail())) {
			throw new UserAlreadyExistsException("L'email " + utenteDTO.getEmail() + " è già "
					+ "associato a un account"); 
		}
		
		if(utenteRepository.existsByNickname(utenteDTO.getNickname())) {
			throw new UserAlreadyExistsException("Il nickname scelto non è disponibile");
		}
		
		//Creazione dell'utente 
		Utente nuovoUtente = new Utente(); 
		//il DTO, per come è configurato, controlla che tutti i campi siano validi e non nulli 
		//non serve che faccia i controlli qui 
		nuovoUtente.setNome(utenteDTO.getNome()); 
		nuovoUtente.setCognome(utenteDTO.getCognome());
		nuovoUtente.setEmail(utenteDTO.getEmail());
		nuovoUtente.setNickname(utenteDTO.getNickname());
		nuovoUtente.setEta(utenteDTO.getEta());
		nuovoUtente.setPassword(utenteDTO.getPassword());
		//il nuovo ruolo è ora USER 
		nuovoUtente.setRuolo(EnumRuolo.USER);
		nuovoUtente.setStatus(EnumStatus.ATTIVO);
		return utenteRepository.save(nuovoUtente);
	}

	@Override
	public String loginUtente(Utente utente) {
		//IMPORTANTE: DEVO RICHIAMARE IL METODO banAccount --> se sei bannato, non puoi fare il login
		
		return null;
	}

	@Override
	public void disattivazioneAccountUtente(Long id) {
		Optional<Utente> utenteOpt = utenteRepository.findById(id);
		Utente utenteEsistente = utenteOpt.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non"
				+ " trovato"));
		
		utenteEsistente.setStatus(EnumStatus.DISATTIVO);
		utenteRepository.save(utenteEsistente);
		
	}

	@Override
	public void riattivazioneAccountUtente(Long id) {
		Optional<Utente> utenteOpt = utenteRepository.findById(id);
		Utente utenteEsistente = utenteOpt.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non"
				+ " trovato"));
		
		utenteEsistente.setStatus(EnumStatus.ATTIVO);
		utenteRepository.save(utenteEsistente); 
		
	}

	@Override
	//solo l'admin può farlo --> La chiamata API del Controller si assicurerà che lo eseguirà 
	//solo l'admin
	public void banAccount(Long id) {
		Optional<Utente> utenteOpt = utenteRepository.findById(id);
		Utente utenteEsistente = utenteOpt.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non"
				+ " trovato"));
		
		utenteEsistente.setStatus(EnumStatus.BANNATO);
		utenteRepository.save(utenteEsistente);
		
	}

	@Override
	public void deleteUtenteById(Long id) {
		
		if(id == null) {
			throw new IllegalArgumentException("Id " + id + " nullo"); 
		}
		
		Optional<Utente> utenteOpt = utenteRepository.findById(id); 
		Utente utenteEsistente = utenteOpt.orElseThrow(() -> new NotFoundException("Utente con id " + id + " non"
				+ " trovato"));
		
		utenteRepository.delete(utenteEsistente);
		
		
	}


}
