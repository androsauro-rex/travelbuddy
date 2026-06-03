package com.travelbuddy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class TappaDTO {
	
	@NotBlank(message = "Il nome della tappa è obbligatorio e non può essere vuoto")
	private String nomeTappa;
	
	@Size(max = 2000, message = "Il testo non può superare i 2000 caratteri")
	private String descrizioneTappa;
	
}
