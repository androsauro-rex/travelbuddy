package com.travelbuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelbuddy.entity.Diario;

public interface DiarioRepository extends JpaRepository<Diario, Long>{
	
	//in diario abbiamo la foreignKey idUtente
	//questo utente ha un diario? Sì o no => boolean
	boolean existsByUtenteId(Long idUtente);
	
	//trovare il diario a partire dall'id dell'utente 
	Optional<Diario> findByUtenteId(Long idUtente); 
	
	
	//sapendo l'id dell'itinerario, voglio trovare il diario 
	Optional<Diario> findByItinerarioId(Long idItinerario);
	
	
	
	
	
	
	
	
	
	
	
	
}
