package com.travelbuddy.service;

import java.util.List;

import com.travelbuddy.dto.SpesaDTO;
import com.travelbuddy.entity.Spesa;

public interface SpesaService {
	
	List<Spesa> findAllSpeseByItinerarioId(Long idItinerario);
	Spesa findSpesaById(Long id); 
	Spesa aggiungiSpesa(Long idItinerario, SpesaDTO spesaDTO); 
	void deleteSpesaById(Long id); 
	
}
