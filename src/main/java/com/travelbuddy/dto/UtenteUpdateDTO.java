package com.travelbuddy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UtenteUpdateDTO {
	
	@NotBlank(message = "Il nome è obbligatorio e non può essere vuoto")
	private String nome;
	
	@NotBlank(message = "Il cognome è obbligatorio e non può essere vuoto")
	private String cognome;
	
	@NotBlank(message = "Il nickname è obbligatorio e non può essere vuoto")
	private String nickname;
	
	@NotBlank(message = "L'email è obbligatoria e tale campo non può essere vuota")
	@Email(message = "Inserisci un indirizzo email valido")
	private String email;
	
	@NotNull(message = "Età Obbligatoria")
	@Min(value = 18, message = "L'età deve essere maggiore di 18")
	private Integer eta;
	
	
}
