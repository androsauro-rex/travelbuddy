package com.travelbuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelbuddy.entity.Destinazione;

public interface DestinazioneRepository extends JpaRepository<Destinazione, Long>{
	
	//trova tutte le destinazioni dato l'Id dell'itinerario
	List<Destinazione> findByItinerarioId(Long idItinerario); 
	
//	la query scritta sotto è corretta di per sè (funziona) ma non va bene per l'applicazione 
//	perchè TitoloViaggio non è unique, quindi se ho due 
//	viaggi che entrambi hanno come titolo "Viaggio in Toscana" e Id diversi dove il primo  
//	ha 3 destinazioni e il secondo 4, la query restituisce tutte e 7 le destinazioni 
//	se si vuole solo un elenco delle destinazioni potrebbe avere un senso mettere distinct
//	//dato un titolo di un viaggio o una "sottostringa" del titolo (da qui Containing), 
//	//trovo tutte le destinazioni di quel viaggio 
//	List<Destinazione> findByItinerarioTitoloViaggioContainingIgnoreCase(String titoloViaggio);
}
