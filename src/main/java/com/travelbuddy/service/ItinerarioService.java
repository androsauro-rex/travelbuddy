package com.travelbuddy.service;

import java.util.List;

import com.travelbuddy.dto.DestinazioneDTO;
import com.travelbuddy.dto.GiornoDTO;
import com.travelbuddy.dto.ItinerarioCreateDTO;
import com.travelbuddy.dto.ItinerarioUpdateDTO;
import com.travelbuddy.dto.TappaDTO;
import com.travelbuddy.entity.Itinerario;

public interface ItinerarioService {
	
	List<Itinerario> findAllItinerari(); 
	Itinerario findItinerarioById(Long id); 
	Itinerario creaItinerario(ItinerarioCreateDTO itinerarioDTO, 
			DestinazioneDTO destinazioneDTO); 
	Itinerario creaItinerarioConGiorni(ItinerarioCreateDTO itinerarioDTO, 
			DestinazioneDTO destinazioneDTO, 
			List<GiornoDTO> listaGiorni); 
	Itinerario creaItinerarioConGiorniTappe(ItinerarioCreateDTO itinerarioDTO, 
			DestinazioneDTO destinazioneDTO, 
			List<GiornoDTO> listaGiorni, 
			List<TappaDTO> listaTappe); 
	Itinerario modificaItinerario(ItinerarioUpdateDTO itinerarioDTO, Long id);   
	void aggiuntaLike(Itinerario itinerario); 
	//trovi l'id dell'itinerario -> getLike -> incrementi like 
	void rimuoviLike(Itinerario itinerario); 
//	void updateVisibilitaItinerario(Itinerario itinerario);
	
	//IN FUTURO: List<Itinerario> listaItinerariCheTiPiacciono(); //mettere i parametri corretti
	
	void deleteItinerarioById(Long id); 
	
	
	
}
