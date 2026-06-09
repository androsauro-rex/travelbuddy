package com.travelbuddy.controller;

import org.springframework.http.ResponseEntity;
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

	@GetMapping("/common/itinerari/{id}")
	public ResponseEntity<Itinerario> getItinerario(@Min(1) @PathVariable Long id) {
		return ResponseEntity.ok(itinerarioService.findItinerarioById(id));
	}

	@PostMapping("/user/creazione/itinerario")
	public ResponseEntity<Itinerario> creaItinerario(@Valid @RequestBody ItinerarioDestinazioneDTO DTO) {
		return ResponseEntity.ok(itinerarioService.creaItinerario(
				DTO.getItinerarioCreateDTO(), DTO.getDestinazioneDTO()));
	}

	// QUESTO è l'endpoint che chiami dal frontend.
	// Ora il service salva anche le tappe (sono dentro ogni GiornoDTO).
	@PostMapping("/user/creazione/itinerario/con/giorni")
	public ResponseEntity<Itinerario> creaItinerarioConGiorni(
			@Valid @RequestBody ItinerarioDestinazioneGiornoDTO DTO,
			@Min(1) @PathVariable Long idUtente) {
		return ResponseEntity.ok(itinerarioService.creaItinerarioConGiorni(DTO.getItinerarioCreateDTO(), 
				DTO.getDestinazioneDTO(), DTO.getGiornoDTO(), idUtente));
	}

	// MODIFICA: ho aggiunto /{idItinerario} nel path (prima mancava!)
	@PutMapping("/user/modifica/itinerario/{idItinerario}")
	public ResponseEntity<Void> modificaItinerario(
			@Valid @RequestBody ModificaItinerarioDTO DTO,
			@Min(1) @PathVariable Long idItinerario) {
		itinerarioService.modificaItinerario(DTO.getItinerarioDTO(), idItinerario, DTO.getGiornoDTO());
		return ResponseEntity.noContent().build();
	}

	// CANCELLAZIONE
	@DeleteMapping("/user/itinerario/{idItinerario}")
	public ResponseEntity<Void> eliminaItinerario(@Min(1) @PathVariable Long idItinerario) {
		itinerarioService.deleteItinerarioById(idItinerario);
		return ResponseEntity.noContent().build();
	}

}