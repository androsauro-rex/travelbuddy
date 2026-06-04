package com.travelbuddy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TappaDTO {
	
	@NotBlank(message = "Il nome della tappa è obbligatorio e non può essere vuoto")
	private String nomeTappa;
	
	@Size(max = 2000, message = "Il testo non può superare i 2000 caratteri")
	private String descrizioneTappa;
	
}
