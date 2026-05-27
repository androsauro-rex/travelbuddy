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
	private Long id;
	
	@Column(nullable = false, precision = 10, scale = 2)
	@NotNull(message = "Budget Pianificato Obbligatorio")
	@Digits(fraction = 2, integer = 8, message = "Il Budget Pianificato da te inserito non ha un formato corretto")
	@Positive(message = "Budget deve essere maggiore di 0")
	private BigDecimal pianificato;
	
	@Column(nullable = false, name = "costo_volo_viaggio", precision = 10, scale = 2)
	@NotNull(message = "costo volo viaggio obbligatorio")
	@Digits(fraction = 2, integer = 8, message = "Il costo del volo da te inserito non ha un formato corretto")
	@Positive(message = "Il costo del volo di un viaggio deve essere maggiore di 0")
	private BigDecimal costoVoloViaggio; 
	
	@Column(name = "costo_pernottamento", precision = 10, scale = 2)
	@Digits(fraction = 2, integer = 8, message = "Il costo del pernottamento da te inserito non ha un formato corretto")
	@Positive(message = "Il costo del pernottamento deve essere maggiore di 0")
	private BigDecimal costoPernottamento; 
	
	@Column(name = "spese_cibo", precision = 10, scale = 2)
	@Digits(fraction = 2, integer = 8, message = "Il costo speso per il cibo da te inserito non ha un formato corretto")
	@Positive(message = "Il costo speso per il cibo deve essere maggiore di 0")
	private BigDecimal cibo; 
	
	@Column(name = "spese_extra", precision = 10, scale = 2)
	@Digits(fraction = 2, integer = 8, message = "Il costo delle spese extra da te inserito non ha un formato corretto")
	@Positive(message = "Il costo delle spese extra deve essere maggiore di 0")
	private BigDecimal extra; 
	
}
