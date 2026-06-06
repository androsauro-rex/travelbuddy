package com.travelbuddy.dto;

import java.util.List;

import com.travelbuddy.entity.Giorno;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ModificaItinerarioDTO {
	
	private final ItinerarioUpdateDTO itinerarioDTO; 
	private final List<Giorno> giornoDTO; 
	
}
