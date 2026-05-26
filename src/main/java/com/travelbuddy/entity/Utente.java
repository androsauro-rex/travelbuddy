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
	private String nome;
	@Column(nullable = false, length = 128)
	private String cognome;
	@Column(nullable = false, length = 128, unique = true)
	private String nickname;
	@Column(nullable = false, length = 128)
	private String password;
	@Column(nullable = false, length = 128, unique = true)
	private String email;
	@Column(nullable = false, length = 4)
	private Integer eta;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRuolo ruolo;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	
	
}
