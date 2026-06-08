package com.travelbuddy.dto;

import java.math.BigDecimal;


import com.travelbuddy.listaenum.EnumTipologiaSpesa;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpesaDTO {
	
	@NotNull(message = "Devi selezionare una tipologia di spesa")
	@Enumerated(EnumType.STRING)
	private EnumTipologiaSpesa tipologia; 
	
//	@NotNull(message = "Data della spesa obbligatoria")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	private LocalDate data; 
	
	@NotNull(message = "Il costo della spesa che vuoi inserire non può essere nullo")
	@Digits(fraction = 2, integer = 8, message = "Il costo della spesa te inserito non ha un formato corretto")
	@Positive(message = "Il costo di questa spesa deve essere maggiore di 0")
	private BigDecimal costo; 
	
	
	@NotBlank(message = "la descrizione della spesa, se vuoi metterla, non può essere vuota")
	private String descrizioneSpesa; 
	
}
