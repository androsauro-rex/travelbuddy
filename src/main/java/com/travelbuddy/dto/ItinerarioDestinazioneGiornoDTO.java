package com.travelbuddy.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItinerarioDestinazioneGiornoDTO {
	
	private final ItinerarioCreateDTO itinerarioCreateDTO; 
	private final DestinazioneDTO destinazioneDTO; 
	private final List<GiornoDTO> giornoDTO; 
	
	public ItinerarioDestinazioneGiornoDTO(ItinerarioCreateDTO itinerarioCreateDTO, 
			DestinazioneDTO destinazioneDTO, 
			List<GiornoDTO> giornoDTO) {
		this.itinerarioCreateDTO = itinerarioCreateDTO; 
		this.destinazioneDTO = destinazioneDTO; 
		this.giornoDTO = giornoDTO; 
	}
	
}
