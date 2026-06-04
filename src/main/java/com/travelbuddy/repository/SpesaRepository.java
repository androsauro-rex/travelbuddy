package com.travelbuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelbuddy.entity.Spesa;

public interface SpesaRepository extends JpaRepository<Spesa, Long>{
	
	//dato l'id dell'itinerario, trova la lista di spese effettuate  
	List<Spesa> findByItinerarioId(Long idItinerario);
	
	//alternativa con JPQL: 
//	@Query("SELECT s FROM Spesa s WHERE s.itinerario.id = :idItinerario")
//	List<Spesa> findByItinerarioId(@Param("idItinerario") Long idItinerario);
	
	
}
