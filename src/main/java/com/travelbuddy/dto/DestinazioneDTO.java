package com.travelbuddy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class DestinazioneDTO {

	@NotBlank(message = "Il nome della destinazione è obbligatorio e non può essere vuoto")
	private String nomeDestinazione;
	
}
