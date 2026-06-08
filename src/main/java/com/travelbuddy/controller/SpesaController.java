package com.travelbuddy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelbuddy.dto.SpesaDTO;
import com.travelbuddy.entity.Spesa;
import com.travelbuddy.service.ItinerarioService;
import com.travelbuddy.service.SpesaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1")
public class SpesaController {
	
	private final SpesaService spesaService; 
	
	public SpesaController(SpesaService spesaService, ItinerarioService itinerarioService) {
		this.spesaService = spesaService;
	} 
	
	
	@GetMapping("/common/vedi/spese/itinerario/{id}")
	public ResponseEntity<List<Spesa>> getAllSpeseItinerario(@Min(1) @PathVariable Long id){
		return new ResponseEntity<>(spesaService.findAllSpeseByItinerarioId(id), HttpStatus.OK); 
	}
	
	@GetMapping("/common/vedi/spesa/{id}")
	public ResponseEntity<Spesa> getSpesa(@Min(1) @PathVariable Long id){
		return ResponseEntity.ok(spesaService.findSpesaById(id)); 
	}
	
	@PostMapping("/common/aggiungi/spesa")
	public ResponseEntity<Spesa> addSpesa(@Valid @RequestBody SpesaDTO DTO, 
			@Min(1) @PathVariable Long id){
		return ResponseEntity.ok(spesaService.aggiungiSpesa(id, DTO)); 
	}
	
	@DeleteMapping("/common(elimina/spesa/{id}")
	public ResponseEntity<Void> deleteSpesa(@Min(1) @PathVariable Long id){
		spesaService.deleteSpesaById(id); 
		return ResponseEntity.ok().build(); 
	}
	
}
