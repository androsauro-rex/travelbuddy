package com.travelbuddy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Destinazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(name = "nome_destinazione", nullable = false, length = 128)
	@NotNull(message = "Nome Destinazione Obbligatorio")
	@NotBlank(message = "Il nome  della destinazione non può essere vuoto")
	private String nomeDestinazione;
	
	@ManyToOne
	@JoinColumn(name = "idItinerario")
	private Itinerario itinerario; 
	
}
