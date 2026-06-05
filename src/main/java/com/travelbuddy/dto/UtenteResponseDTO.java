package com.travelbuddy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UtenteResponseDTO {
	
	@NotBlank(message = "Il nome è obbligatorio e non può essere vuoto")
	private String nome;
	
	@NotBlank(message = "Il cognome è obbligatorio e non può essere vuoto")
	private String cognome;
	
	@NotBlank(message = "Il nickname è obbligatorio e non può essere vuoto")
	private String nickname;
	
	
}
