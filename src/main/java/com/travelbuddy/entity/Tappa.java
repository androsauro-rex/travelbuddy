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
@Table(name = "tappe")
public class Tappa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(name = "nome_tappa", nullable = false, length = 128)
	@NotBlank(message = "Il nome della tappa è obbligatorio e non può essere vuoto")
	private String nomeTappa;
	
	@Column(name = "descrizione_tappa", length = 2000, nullable = true)
	private String descrizioneTappa;
	
	@ManyToOne 
	@JoinColumn(name = "idGiorno")
	private Giorno giorno; 

}