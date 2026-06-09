package com.travelbuddy.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.travelbuddy.entity.Giorno;
import com.travelbuddy.exception.NotFoundException;
import com.travelbuddy.repository.GiornoRepository;

@Service
public class GiornoServiceImpl implements GiornoService{
	
	private final GiornoRepository giornoRepository; 
	
	public GiornoServiceImpl(GiornoRepository giornoRepository) {
		this.giornoRepository = giornoRepository; 
	}
	
	@Override
	public void deleteGiornoById(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
		Optional<Giorno> giornoOpt = giornoRepository.findById(id); 
		Giorno giorno = giornoOpt.orElseThrow(() -> new NotFoundException("Giorno "
				+ "con id " + id + " non trovato")); 
		giornoRepository.delete(giorno);
		
	}

}
