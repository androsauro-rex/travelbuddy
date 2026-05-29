package com.travelbuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelbuddy.entity.Spesa;

public interface SpesaRepository extends JpaRepository<Spesa, Long>{
	
	@Query("SELECT s FROM Spesa s JOIN Itinerario i WHERE i.id = :idItinerario")
	List<Spesa> findAllSpeseByItinerarioId(@Param("idItinerario") Long idItinerario);
	
	
}
