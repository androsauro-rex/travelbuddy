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
@Table(name = "users")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Integer id;
	@Column(nullable = false, length = 128)
	@NotNull(message = "Nome Obbligatorio")
	private String nome;
	@Column(nullable = false, length = 128)
	@NotNull(message = "Cognome Obbligatorio")
	private String cognome;
	@Column(nullable = false, length = 128, unique = true)
	@NotNull(message = "Nickname Obbligatorio")
	private String nickname;
	@Column(nullable = false, length = 128)
	@NotNull(message = "Password Obbligatoria")
	private String password;
	@Column(nullable = false, length = 128, unique = true)
	@NotNull(message = "Email Obbligatorio")
	private String email;
	@Column(nullable = false, length = 4)
	@NotNull(message = "Età Obbligatoria")
	private Integer eta;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRuolo ruolo;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	
	
}
