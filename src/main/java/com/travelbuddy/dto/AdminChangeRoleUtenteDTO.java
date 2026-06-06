package com.travelbuddy.dto;

import com.travelbuddy.listaenum.EnumRoles;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminChangeRoleUtenteDTO {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private EnumRoles ruolo;
	
}
