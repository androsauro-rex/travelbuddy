package com.travelbuddy.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ItinerarioDestinazioneGiornoTappaDTO {

	private final ItinerarioCreateDTO itinerarioCreateDTO; 
	private final DestinazioneDTO destinazioneDTO; 
	private final List<GiornoDTO> giornoDTO; 
	private final List<TappaDTO> tappaDTO;


	public ItinerarioDestinazioneGiornoTappaDTO(ItinerarioCreateDTO itinerarioCreateDTO, 
			DestinazioneDTO destinazioneDTO,  
			List<GiornoDTO> giornoDTO,List<TappaDTO> tappaDTO) {
		this.itinerarioCreateDTO = itinerarioCreateDTO; 
		this.destinazioneDTO = destinazioneDTO; 
		this.giornoDTO = giornoDTO; 
		this.tappaDTO = tappaDTO;
	}

}
