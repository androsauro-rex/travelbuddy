package com.travelbuddy.service;

import java.util.List;

import com.travelbuddy.dto.DestinazioneDTO;
import com.travelbuddy.dto.GiornoDTO;
import com.travelbuddy.dto.ItinerarioCreateDTO;
import com.travelbuddy.dto.ItinerarioUpdateDTO;
import com.travelbuddy.entity.Itinerario;

public interface ItinerarioService {

	List<Itinerario> findAllItinerari();
	Itinerario findItinerarioById(Long id);

	Itinerario creaItinerario(ItinerarioCreateDTO itinerarioDTO,
			DestinazioneDTO destinazioneDTO);

	Itinerario creaItinerarioConGiorni(ItinerarioCreateDTO itinerarioDTO,
			DestinazioneDTO destinazioneDTO,
			List<GiornoDTO> listaGiorni, 
			Long idUtente);

//	Itinerario creaItinerarioConGiorniTappe(ItinerarioCreateDTO itinerarioDTO,
//			DestinazioneDTO destinazioneDTO,
//			List<GiornoDTO> listaGiorni,
//			List<TappaDTO> listaTappe);

	// ATTENZIONE: ora riceve List<GiornoDTO> (non List<Giorno> entity)
	Itinerario modificaItinerario(ItinerarioUpdateDTO itinerarioDTO, Long id,
			List<GiornoDTO> listaGiorni);

	void aggiuntaLike(Itinerario itinerario);
	void rimuoviLike(Itinerario itinerario);

	void deleteItinerarioById(Long id);

}