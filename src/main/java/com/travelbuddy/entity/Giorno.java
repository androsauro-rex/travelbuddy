package com.travelbuddy.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "giorni")
public class Giorno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(name = "descrizione_attivita_giorno", nullable = false)
	@NotBlank(message = "Devi dare un nome al giorno o scrivere un elenco delle attività da fare")
	private String descrizioneAttivitaGiorno; 
	
	@Column(nullable = false)
	@NotNull(message = "Data Obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)	
	private LocalDate data;
	
	@ManyToOne
	@JoinColumn(name = "idItinerario")
	private Itinerario itinerario; 


}
