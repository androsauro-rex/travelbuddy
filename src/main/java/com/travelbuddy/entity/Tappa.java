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
@Table(name = "tappe")
public class Tappa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(name = "nome_tappa", nullable = false, length = 128)
	@NotNull(message = "Nome Tappa Obbligatorio")
	@NotBlank(message = "Il nome della tappa non può essere vuoto")
	private String nomeTappa;
	
	@Column(length = 2000)
	@NotBlank(message = "La recensione non può essere vuota")
	private String recensione;
	
	@ManyToOne 
	@JoinColumn(name = "idGiorno")
	private Giorno giorno; 

}
