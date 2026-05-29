package com.travelbuddy.entity;

import java.math.BigDecimal;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.travelbuddy.listaenum.EnumVisibilita;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "itinerari")
public class Itinerario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(name = "titolo_viaggio", length = 128, nullable = false)
	@NotNull(message = "Titolo Obbligatorio")
	@NotBlank(message = "Il titolo del viaggio non può essere vuoto")
	private String titoloViaggio;
	
	@Column(nullable = false)
	@Min(value = 0, message = "I like non possono essere negativi")
	private int likes;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumVisibilita visibilita;
	
	@Column(name = "data_inizio_viaggio", nullable = false)
	@NotNull(message = "Data Obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataInizioViaggio;
	
	@Column(name = "data_fine_viaggio", nullable = false)
	@NotNull(message = "Data Obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataFineViaggio;
	
	@Column(name = "budget_pianificato", nullable = false, precision = 10, scale = 2)
	@NotNull(message = "Budget Pianificato Obbligatorio")
	@Digits(fraction = 2, integer = 8, message = "Il Budget Pianificato da te inserito non ha un formato corretto")
	@Positive(message = "Budget deve essere maggiore di 0")
	private BigDecimal budgetPianificato; 
	
	@ManyToOne
	@JoinColumn(name = "idUtente")
	private Utente utente; 
	
	
	@ManyToOne
	@JoinColumn(name = "idSpesa")
	private Spesa spesa; 

}
