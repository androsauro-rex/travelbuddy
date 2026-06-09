package com.travelbuddy.dto;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Versione corretta per ricevere JSON dal frontend.
 *
 * COSA È CAMBIATO rispetto alla tua versione:
 *  - tolti i "final" dai campi
 *  - aggiunto @NoArgsConstructor (costruttore vuoto)
 *  - tolto il costruttore parametrico esplicito
 *
 * PERCHÉ: Jackson (la libreria che trasforma il JSON che arriva dal frontend
 * in un oggetto Java) ha bisogno di creare l'oggetto vuoto e poi riempirlo
 * con i setter. Con i campi "final" non può scrivere nei campi, e senza
 * costruttore vuoto non può nemmeno creare l'oggetto. Risultato: il JSON
 * verrebbe rifiutato con un errore di deserializzazione PRIMA di arrivare
 * al tuo service.
 */
@Getter
@Setter
@NoArgsConstructor
public class ItinerarioDestinazioneGiornoDTO {

	@Valid
	private ItinerarioCreateDTO itinerarioCreateDTO;

	@Valid
	private DestinazioneDTO destinazioneDTO;

	@Valid
	private List<GiornoDTO> giornoDTO;

}