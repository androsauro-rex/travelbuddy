package com.travelbuddy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DestinazioneDTO {

	@NotBlank(message = "Il nome della destinazione è obbligatorio e non può essere vuoto")
	private String nomeDestinazione;
	
}
