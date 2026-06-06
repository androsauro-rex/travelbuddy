package com.travelbuddy.dto;

import com.travelbuddy.listaenum.EnumRoles; 
import com.travelbuddy.listaenum.EnumStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUtenteDTO {
	
	
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
	@Positive(message = "L'età deve essere maggiore di 18")
	private Integer eta;
	
	//dubbio nel mettere la password
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRoles ruolo;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;

	
}
