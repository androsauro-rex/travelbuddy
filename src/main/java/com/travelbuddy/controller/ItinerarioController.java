package com.travelbuddy.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbuddy.dto.GiornoCreateDTO;
import com.travelbuddy.service.GiornoService;
import com.travelbuddy.service.ItinerarioService;

@RestController
@RequestMapping("/api/v1")
public class ItinerarioController {
	
	private final ItinerarioService itinerarioService; 
	private final GiornoService giornoService; 
	//per il polimorfismo 
	//definisco il tipo, implementazione del sottotipo
	public ItinerarioController(ItinerarioService itinerarioService, 
			GiornoService giornoService) {
		this.itinerarioService = itinerarioService; 
		this.giornoService = giornoService; 
	}
	
//	@PatchMapping()
//	public ResponseEntity<void>addDaysToExistItinerary(@RequestParam Long idItinerario, @RequestBody GiornoCreateDTO body){
//		return itinerarioService.modificaItinerario(body.getGiorniDaAggiungere(), idItinerario);
//	}
	
	
}
