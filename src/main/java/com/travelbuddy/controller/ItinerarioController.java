package com.travelbuddy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
	//per il polimorfismo 
	//definisco il tipo, implementazione del sottotipo
	public ItinerarioController(ItinerarioService itinerarioService) {
		this.itinerarioService = itinerarioService;  
	}
	
	@GetMapping("/common/itinerari/{id}")
	public ResponseEntity<Itinerario> getAllItinerari(@Min(1) @PathVariable Long id){
		return ResponseEntity.ok(itinerarioService.findItinerarioById(id)); 
	}
	
	@PostMapping("/user/creazione/itinerario")
	public ResponseEntity<Itinerario> creaItinerario(@Valid @RequestBody ItinerarioDestinazioneDTO DTO){
		return ResponseEntity.ok(itinerarioService.creaItinerario(DTO.getItinerarioCreateDTO(), DTO.getDestinazioneDTO()));
	}
	
	@PostMapping("/user/creazione/itinerario/con/giorni")
	public ResponseEntity<Itinerario> creaItinerarioConGiorni(@Valid @RequestBody 
			ItinerarioDestinazioneGiornoDTO DTO){
		return ResponseEntity.ok(itinerarioService.creaItinerarioConGiorni(DTO.getItinerarioCreateDTO(), 
				DTO.getDestinazioneDTO(), DTO.getGiornoDTO())); 
	}
	
//	@PostMapping("/user/creazione/itinerario/con/giorni/e/tappe")
//	public ResponseEntity<Itinerario> creaItinerarioConGiorniTappe(@Valid @RequestBody ItinerarioDestinazioneGiornoTappaDTO DTO){
//		return ResponseEntity.ok(itinerarioService.creaItinerarioConGiorniTappe(DTO.getItinerarioCreateDTO(), DTO.getDestinazioneDTO(), DTO.getGiornoDTO(), DTO.getTappaDTO()));
//	}
	
	
	@PutMapping("/user/modifica/itinerario")
	public ResponseEntity<?> modificaItinerario(@Valid @RequestBody 
			ModificaItinerarioDTO DTO, @Min(1) @PathVariable Long IdItinerario){
		itinerarioService.modificaItinerario(DTO.getItinerarioDTO(), IdItinerario, DTO.getGiornoDTO());
		return (ResponseEntity<?>) ResponseEntity.noContent();
	}
	
}
