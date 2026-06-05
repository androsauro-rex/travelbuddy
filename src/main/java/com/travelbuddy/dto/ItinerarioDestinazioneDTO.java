package com.travelbuddy.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItinerarioDestinazioneDTO {
	
	private final ItinerarioCreateDTO itinerarioCreateDTO; 
	private final DestinazioneDTO destinazioneDTO; 
	
	public ItinerarioDestinazioneDTO(ItinerarioCreateDTO itinerarioCreateDTO, 
			DestinazioneDTO destinazioneDTO) {
		this.itinerarioCreateDTO = itinerarioCreateDTO; 
		this.destinazioneDTO = destinazioneDTO; 
	}
	
}
