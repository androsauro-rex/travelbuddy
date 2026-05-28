package com.travelbuddy.entity;

import com.travelbuddy.entity.Utente;
import com.travelbuddy.listaenum.EnumRuolo;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
	@NotNull(message = "Nome Obbligatorio")
	@NotBlank(message = "Il nome non può essere vuoto")
	private String nome;
	
	@Column(nullable = false, length = 128)
	@NotNull(message = "Cognome Obbligatorio")
	@NotBlank(message = "Il cognome non può essere vuoto")
	private String cognome;
	
	@Column(nullable = false, length = 50, unique = true)
	@NotNull(message = "Nickname Obbligatorio")
	@NotBlank(message = "Il nickname non può essere vuoto")
	private String nickname;
	
	@Column(nullable = false)
	@Size(min = 6, max = 20, message = "La password deve contenere almeno 6 caratteri e al massimo 20")
	@NotNull(message = "Password Obbligatoria")
	private String password;
	
	@Column(nullable = false, length = 128, unique = true)
	@NotNull(message = "Email Obbligatorio")
	@NotBlank(message = "L'email non può essere vuota")
	@Email(message = "Inserisci un indirizzo email valido")
	private String email;
	
	@Column(nullable = false, length = 4)
	@NotNull(message = "Età Obbligatoria")
	@Positive(message = "L'età deve essere maggiore di 18")
	private Integer eta;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRuolo ruolo;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	
	
}
