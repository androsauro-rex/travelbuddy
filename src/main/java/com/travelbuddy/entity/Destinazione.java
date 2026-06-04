package com.travelbuddy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "destinazioni")
public class Destinazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(name = "nome_destinazione", nullable = false, length = 128)
	@NotBlank(message = "Il nome della destinazione è obbligatorio e non può essere vuoto")
	private String nomeDestinazione;
	
	@ManyToOne
	@JoinColumn(name = "idItinerario")
	private Itinerario itinerario; 
	
}
