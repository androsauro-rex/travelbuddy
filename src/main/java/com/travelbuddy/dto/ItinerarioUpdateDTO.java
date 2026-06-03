package com.travelbuddy.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.travelbuddy.listaenum.EnumVisibilita;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItinerarioUpdateDTO {
	
	@NotBlank(message = "Il titolo del viaggio è obbligatorio e non può essere vuoto")
	private String titoloViaggio;
	
	
	@Enumerated(EnumType.STRING)
	private EnumVisibilita visibilita;
	

	@NotNull(message = "Data Obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataInizioViaggio;
	

	@NotNull(message = "Data Obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataFineViaggio;
	
	
	@NotNull(message = "Budget Pianificato Obbligatorio")
	@Digits(fraction = 2, integer = 8, message = "Il Budget Pianificato da te inserito non ha un formato corretto")
	@Positive(message = "Budget deve essere maggiore di 0")
	private BigDecimal budgetPianificato; 
	
	@Size(max = 2000, message = "Il testo non può superare i 2000 caratteri")
	private String recensioneItinerario; 
	
}
