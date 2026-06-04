package com.travelbuddy.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GiornoCreateDTO {
	
	private List<GiornoDTO> giorniDaAggiungere;
	//JSON con le parentesi graffe che, al suo interno, avrà un array di giorni
}
