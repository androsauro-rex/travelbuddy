package com.travelbuddy.entity;

import com.travelbuddy.entity.Utente;
import com.travelbuddy.listaenum.EnumRoles;

import com.travelbuddy.listaenum.EnumStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "users")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Column(nullable = false, length = 128) 
	@NotBlank(message = "Il nome è obbligatorio e non può essere vuoto")
	private String nome;
	
	@Column(nullable = false, length = 128)
	@NotBlank(message = "Il cognome è obbligatorio e non può essere vuoto")
	private String cognome;
	
	@Column(nullable = false, length = 50, unique = true)
	@NotBlank(message = "Il nickname è obbligatorio e non può essere vuoto")
	private String nickname;
	
	@Column(nullable = false)
	@Size(min = 6, max = 20, message = "La password deve contenere almeno 6 caratteri e al massimo 20")
	@NotBlank(message = "Password Obbligatoria")
	private String password;
	
	@Column(nullable = false, length = 128, unique = true)
	@NotBlank(message = "L'email è obbligatoria e tale campo non può essere vuota")
	@Email(message = "Inserisci un indirizzo email valido")
	private String email;
	
	@Column(nullable = false, length = 3)
	@NotNull(message = "Età Obbligatoria")
	@Min(value = 18, message = "L'età deve essere maggiore di 18")
	private Integer eta;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRoles ruolo;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	
	
}
