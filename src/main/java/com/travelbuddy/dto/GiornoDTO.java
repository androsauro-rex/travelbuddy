package com.travelbuddy.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
public class GiornoDTO {
	
	@NotBlank(message = "Devi dare un nome al giorno o scrivere un elenco delle attività da fare")
	private String descrizioneAttivitaGiorno; 
	
	@NotNull(message = "Data Obbligatoria")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)	
	private LocalDate data;
	
	// Questa lista serve SOLO al DTO per ricevere i dati dal frontend
    private List<TappaDTO> tappe;
	
}
