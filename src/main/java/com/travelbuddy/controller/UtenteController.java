package com.travelbuddy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelbuddy.dto.UtenteCreateDTO;
import com.travelbuddy.dto.UtenteReplaceDTO;
import com.travelbuddy.dto.UtenteUpdateDTO;
import com.travelbuddy.entity.Utente;
import com.travelbuddy.service.UtenteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/api/v1")
public class UtenteController {
	
	//senza @Validated, annotazioni come @Min, per esempio, non sono sicure di essere applicate 
	//@Valid, invece, va messo prima dei DTO così da prendere le annotation scritte nei DTO 
	
	//dependency injection
	private final UtenteService utenteService; 
	public UtenteController(UtenteService utenteService) {
		this.utenteService = utenteService; 
	}
	
	@GetMapping("/admin/utenti")
	public ResponseEntity<List<Utente>> getAllUtenti(){
		return new ResponseEntity<>(utenteService.findAllUtenti(), HttpStatus.OK);
	}
	
	// API: GET + http://localhost:8080/api/v1/admin/books/1
	@GetMapping("/admin/utenti/{id}")
	public ResponseEntity<Utente> getUtenteById(@Min(1) @PathVariable Long id){
		return new ResponseEntity<>(utenteService.findUtenteById(id), HttpStatus.OK); 
	}
	
	@GetMapping("/public/nickname/{nickname}")
	public ResponseEntity<Utente> getUtenteByNickname(@NotBlank @PathVariable String nickname){
		return ResponseEntity.ok(utenteService.findUtenteByNickname(nickname));
	}
	
	
	@GetMapping("/mod-content/email/{email}")
	public ResponseEntity<Utente> getUtenteByEmail(@Email @PathVariable String email){
		return ResponseEntity.ok(utenteService.findUtenteByEmail(email)); 
	}
	
	
	@PutMapping("/common/utenti/replace/{id}")
	public ResponseEntity<Utente> replaceAllDetailsOfUtente(@Valid @RequestBody UtenteReplaceDTO utenteDTO, 
			@Min(1) @PathVariable Long id){
		return ResponseEntity.ok(utenteService.replaceUtenteById(utenteDTO, id));
	}
	
	@PatchMapping("/common/utenti/update/{id}")
	public ResponseEntity<Utente> updateDetailsOfUtente(@Valid @RequestBody UtenteUpdateDTO utenteDTO, 
			@Min(1) @PathVariable Long id){
		return ResponseEntity.ok(utenteService.updateUtenteById(utenteDTO, id)); 
	}
	
	@PostMapping("/public/registrazione")
	public ResponseEntity<Utente> registrazioneUtente(@Valid @RequestBody UtenteCreateDTO utenteDTO){
		return ResponseEntity.ok(
	            utenteService.RegistrazioneNuovoUtente(utenteDTO)
	    );
	}
	
	//quelli che mancano: 
	// PATCH /api/utenti/{id}/disattiva 
	// PATCH /api/utenti/{id}/riattiva 
	// PATCH /api/utenti/{id}/ban
	// DELETE api/utenti/{id}
	
	
	
	
	
//	  .requestMatchers("/api/v1/public/**").permitAll()
//    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // utile per CORS
//    .requestMatchers("/api/v1/user/**").hasRole("USER")
//    .requestMatchers("/api/v1/moderator/**").hasRole("MODERATOR")
//    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//    .requestMatchers("/api/v1/common/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
//    .requestMatchers("/api/v1/mod-content/**").hasAnyRole("MODERATOR", "ADMIN")
	
}
