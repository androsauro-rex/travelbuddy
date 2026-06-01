package com.travelbuddy.dto;

import com.travelbuddy.listaenum.EnumRuolo;
import com.travelbuddy.listaenum.EnumStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UtenteCreateDTO {
	
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
	
	@Size(min = 6, max = 20, message = "La password deve contenere almeno 6 caratteri e al massimo 20")
	@NotBlank(message = "Password Obbligatoria")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private EnumRuolo ruolo;
	
	@Enumerated(EnumType.STRING)
	private EnumStatus status; 
	
}
