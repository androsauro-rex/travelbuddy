package com.travelbuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelbuddy.entity.Giorno;

public interface GiornoRepository extends JpaRepository<Giorno, Long>{
	
	@Query("SELECT g FROM Giorno g WHERE g.itinerario.id = :idItinerario")
	List<Giorno> findGiornoByItinerarioId(@Param("idItinerario") Long idItinerario); 
	
}
