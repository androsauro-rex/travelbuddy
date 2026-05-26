package com.travelbuddy.entity;

import java.time.LocalDate;

import com.travelbuddy.listaenum.EnumVisibilita;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name = "itinerari")
public class Itinerario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Integer id;
	@Column(name = "titolo_viaggio", length = 128, nullable = false)
	@NotNull(message = "Titolo Obbligatorio")
	private String titoloViaggio;
	@Column(nullable = false)
	@Min(value = 0, message = "I like non possono essere negativi")
	private int like;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumVisibilita visibilità;
	@Column(name = "data_inizio_viaggio", nullable = false)
	@NotNull(message = "Data Obbligatoria")
	private LocalDate dataInizioViaggio;
	@Column(name = "data_fine_viaggio", nullable = false)
	@NotNull(message = "Data Obbligatoria")
	private LocalDate dataFineViaggio;

}
