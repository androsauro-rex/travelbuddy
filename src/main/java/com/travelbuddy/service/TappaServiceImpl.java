package com.travelbuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelbuddy.dto.TappaDTO;
import com.travelbuddy.entity.Giorno;
import com.travelbuddy.entity.Tappa;
import com.travelbuddy.exception.NotFoundException;
import com.travelbuddy.repository.GiornoRepository;
import com.travelbuddy.repository.TappaRepository;
@Service
public class TappaServiceImpl implements TappaService{
	
	public final TappaRepository tappaRepository; 
	public final GiornoRepository giornoRepository; 
	
	public TappaServiceImpl(TappaRepository tappaRepository, 
			GiornoRepository giornoRepository) {
		this.tappaRepository = tappaRepository; 
		this.giornoRepository = giornoRepository; 
	}
	
	@Override
	public List<Tappa> findAllTappeByGiornoId(Long idGiorno) {
		validateId(idGiorno);
		Optional<Giorno> giornoOpt = giornoRepository.findById(idGiorno);
		Giorno giorno = giornoOpt.orElseThrow(() -> new NotFoundException("Giorno con "
				+ "id " + idGiorno + " non trovato")); 	
		return tappaRepository.findByGiornoId(giorno.getId());
	}

	@Override
	public Tappa findTappaById(Long idTappa) {
		validateId(idTappa);
		Optional<Tappa> tappaOpt = tappaRepository.findById(idTappa); 
		Tappa tappa = tappaOpt.orElseThrow(() -> new NotFoundException("Tappa con "
				+ "id " + idTappa + " non trovata")); 	
		return tappa;
	}

	@Override
	@Transactional
	public Tappa aggiungiTappa(Long idGiorno, TappaDTO tappaDTO) {
		validateId(idGiorno);
		if(tappaDTO == null) {
			throw new IllegalArgumentException("Tappa passata nulla"); 
		}
		Optional<Giorno> giornoOpt = giornoRepository.findById(idGiorno); 
		Giorno giorno = giornoOpt.orElseThrow(() -> new NotFoundException("Giorno "
				+ " con id " + idGiorno + " non trovato"));
		Tappa tappa = new Tappa(); 
		tappa.setNomeTappa(tappaDTO.getNomeTappa());
		if(tappaDTO.getDescrizioneTappa() != null) {
			tappa.setDescrizioneTappa(tappaDTO.getNomeTappa());
		}
		tappa.setGiorno(giorno);
		tappaRepository.save(tappa); 
		return tappa;
	}
	
	@Override
	@Transactional
	public Tappa modificaTappa(Long idTappa, TappaDTO tappaDTO) {
		validateId(idTappa);
		if(tappaDTO == null) {
			throw new IllegalArgumentException("Tappa passata nulla"); 
		}
		Optional<Tappa> tappaOpt = tappaRepository.findById(idTappa); 
		Tappa tappa = tappaOpt.orElseThrow(() -> new NotFoundException("Tappa "
				+ " con id " + idTappa + " non trovata"));
		//update: modifichiamo solo i dati che vengono passati dal DTO che non sono null 
		tappa.setNomeTappa(tappaDTO.getNomeTappa());
		if(tappaDTO.getDescrizioneTappa() != null) {
			tappa.setDescrizioneTappa(tappaDTO.getDescrizioneTappa());
		}
		tappaRepository.save(tappa); 
		return tappa;
	}
	

	@Override
	public void deleteTappaById(Long idTappa) {
		validateId(idTappa);
		Optional<Tappa> tappaOpt = tappaRepository.findById(idTappa); 
		Tappa tappa = tappaOpt.orElseThrow(() -> new NotFoundException("Tappa con "
				+ "id " + idTappa + " non trovata")); 
		tappaRepository.delete(tappa);
		
		
	}
	
	//metodo statico (di classe) che verifica che l'id passato nel metodo non sia nullo
	private static void validateId(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
	}

	

}
