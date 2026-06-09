package com.travelbuddy.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelbuddy.dto.ItinerarioDestinazioneDTO;
import com.travelbuddy.dto.ItinerarioDestinazioneGiornoDTO;
import com.travelbuddy.dto.ModificaItinerarioDTO;
import com.travelbuddy.entity.Itinerario;
import com.travelbuddy.service.ItinerarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1")
public class ItinerarioController {

	private final ItinerarioService itinerarioService;

	public ItinerarioController(ItinerarioService itinerarioService) {
		this.itinerarioService = itinerarioService;
	}

	// ===== metodo di comodo: legge l'id utente dal token (dai details) =====
	private Long getAuthUserId() {
		UsernamePasswordAuthenticationToken authentication =
				(UsernamePasswordAuthenticationToken)
				SecurityContextHolder.getContext().getAuthentication();
		return (Long) authentication.getDetails();
	}

	@GetMapping("/common/itinerari/{id}")
	public ResponseEntity<Itinerario> getItinerario(@Min(1) @PathVariable Long id) {
		return ResponseEntity.ok(itinerarioService.findItinerarioById(id));
	}

	@PostMapping("/user/creazione/itinerario")
	public ResponseEntity<Map<String, Object>> creaItinerario(
			@Valid @RequestBody ItinerarioDestinazioneDTO DTO) {

		Long authUserId = getAuthUserId();

		Itinerario creato = itinerarioService.creaItinerario(
				DTO.getItinerarioCreateDTO(), DTO.getDestinazioneDTO(), authUserId);

		// ===== restituisco solo l'id, in JSON semplice e sicuro =====
		return ResponseEntity.ok(Map.of("id", creato.getId()));
	}

	// QUESTO e' l'endpoint che chiami dal frontend.
	@PostMapping("/user/creazione/itinerario/con/giorni")
	public ResponseEntity<Map<String, Object>> creaItinerarioConGiorni(
			@Valid @RequestBody ItinerarioDestinazioneGiornoDTO DTO) {

		Long authUserId = getAuthUserId();

		Itinerario creato = itinerarioService.creaItinerarioConGiorni(
				DTO.getItinerarioCreateDTO(),
				DTO.getDestinazioneDTO(),
				DTO.getGiornoDTO(),
				authUserId);

		// ===== restituisco solo l'id, in JSON semplice e sicuro =====
		return ResponseEntity.ok(Map.of("id", creato.getId()));
	}

	@PutMapping("/user/modifica/itinerario/{idItinerario}")
	public ResponseEntity<Void> modificaItinerario(
			@Valid @RequestBody ModificaItinerarioDTO DTO,
			@Min(1) @PathVariable Long idItinerario) {
		itinerarioService.modificaItinerario(DTO.getItinerarioDTO(), idItinerario, DTO.getGiornoDTO());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/user/itinerario/{idItinerario}")
	public ResponseEntity<Void> eliminaItinerario(@Min(1) @PathVariable Long idItinerario) {
		itinerarioService.deleteItinerarioById(idItinerario);
		return ResponseEntity.noContent().build();
	}
}