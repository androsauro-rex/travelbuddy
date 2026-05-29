package com.travelbuddy.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.travelbuddy.listaenum.EnumTipologiaSpesa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "spese")
public class Spesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Devi selezionare una tipologia di spesa")
	@Enumerated(EnumType.STRING)
	private EnumTipologiaSpesa tipologia; 
	
	@Column(nullable = false)
	@NotNull(message = "Data della spesa obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate data; 
	
	@Column(nullable = false, precision = 10, scale = 2)
	@NotNull(message = "Il costo della spesa che vuoi inserire non può essere nullo")
	@Digits(fraction = 2, integer = 8, message = "Il costo della spesa te inserito non ha un formato corretto")
	@Positive(message = "Il costo di questa spesa deve essere maggiore di 0")
	private BigDecimal costo; 
	
	
	@Column(name = "descrizione_spesa", length = 2000)
	@NotBlank(message = "la descrizione della spesa, se vuoi metterla, non può essere vuota")
	private String descrizioneSpesa; 
	
	
	
	
}
