package com.travelbuddy.service;

import java.util.List;

import com.travelbuddy.dto.TappaDTO;
import com.travelbuddy.entity.Tappa;

public interface TappaService {
	
	List<Tappa> findAllTappeByGiornoId(Long idGiorno); 
	Tappa findTappaById(Long idTappa);
	Tappa aggiungiTappa(Long idGiorno, TappaDTO tappaDTO); 
	void deleteTappaById(Long idTappa); 
	
	
}
