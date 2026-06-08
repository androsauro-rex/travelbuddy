package com.travelbuddy.dto;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Versione corretta:
 *  - usa List<GiornoDTO> (NON List<Giorno> entity)
 *  - @NoArgsConstructor + campi non-final, per Jackson
 *
 * Così il frontend manda i giorni come JSON pulito, e ogni giorno
 * porta dentro le sue tappe.
 */
@Getter
@Setter
@NoArgsConstructor
public class ModificaItinerarioDTO {

	@Valid
	private ItinerarioUpdateDTO itinerarioDTO;

	@Valid
	private List<GiornoDTO> giornoDTO;

}