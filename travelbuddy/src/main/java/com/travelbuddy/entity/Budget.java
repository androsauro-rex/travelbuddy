package com.travelbuddy.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
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
@Table(name = "budget")
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Integer id;
	@Column(nullable = false, precision = 10, scale = 2)
	@NotNull(message = "Budget Pianificato Obbligatorio")
	@Digits(fraction = 2, integer = 8, message = "Budget Pianificato non conforme al formato")
	@Positive(message = "Budget deve essere maggiore di 0")
	private BigDecimal pianificato;
	
	
}
