package com.travelbuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelbuddy.dto.SpesaDTO;
import com.travelbuddy.entity.Itinerario;
import com.travelbuddy.entity.Spesa;
import com.travelbuddy.exception.NotFoundException;
import com.travelbuddy.repository.ItinerarioRepository;
import com.travelbuddy.repository.SpesaRepository;
@Service
public class SpesaServiceImpl implements SpesaService{
	
	private final SpesaRepository spesaRepository; 
	private final ItinerarioRepository itinerarioRepository; 
	
	public SpesaServiceImpl(SpesaRepository spesaRepository, 
			ItinerarioRepository itinerarioRepository) {
		this.spesaRepository = spesaRepository; 
		this.itinerarioRepository = itinerarioRepository; 
	}

	@Override
	public List<Spesa> findAllSpeseByItinerarioId(Long idItinerario) {
		validateId(idItinerario);
		Optional<Itinerario> itinerarioOpt = itinerarioRepository.findById(idItinerario);
		Itinerario itinerario = itinerarioOpt.orElseThrow(()-> new NotFoundException("Itinerario "
				+ " con id " + idItinerario + " non trovato" ));
		return spesaRepository.findByItinerarioId(itinerario.getId());
	}

	@Override
	public Spesa findSpesaById(Long id) {
		validateId(id);
		Optional<Spesa> spesaOpt = spesaRepository.findById(id); 
		Spesa spesa = spesaOpt.orElseThrow(() -> new NotFoundException("Spesa "
				+ " con id " + id + " non trovata" ));
		return spesa; 
	}

	//le spese vengono aggiunte in base all'itinerario, non legata alle tappe
	@Override
	@Transactional
	public Spesa aggiungiSpesa(Long idItinerario, SpesaDTO spesaDTO) {
		validateId(idItinerario);
		if(spesaDTO == null) {
			throw new IllegalArgumentException("dati spesa passati nulli"); 
		}
		Optional<Itinerario> itinerarioOpt = itinerarioRepository.findById(idItinerario);
		Itinerario itinerario = itinerarioOpt.orElseThrow(()-> new NotFoundException("Itinerario "
				+ " con id " + idItinerario + " non trovato" ));
		Spesa spesa = new Spesa(); 
		spesa.setTipologia(spesaDTO.getTipologia());
		spesa.setCosto(spesaDTO.getCosto());
//		spesa.setData(spesaDTO.getData());
		if(spesaDTO.getDescrizioneSpesa() != null) {
			spesa.setDescrizioneSpesa(spesaDTO.getDescrizioneSpesa());
		}
		spesa.setItinerario(itinerario);
		spesaRepository.save(spesa);
		
		return spesa;
	}

	@Override
	public void deleteSpesaById(Long id) {
		validateId(id);
		Optional<Spesa> spesaOpt = spesaRepository.findById(id); 
		Spesa spesa = spesaOpt.orElseThrow(() -> new NotFoundException("Spesa "
				+ " con id " + id + " non trovata" ));
		spesaRepository.delete(spesa);
	}
	
	//metodo statico (di classe) che verifica che l'id passato nel metodo non sia nullo
	private static void validateId(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
	}
	
	
	
	
}
