package com.travelbuddy.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelbuddy.entity.Itinerario;
import com.travelbuddy.listaenum.EnumVisibilita;

public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {
	
	List<Itinerario> findByTitoloViaggioIgnoreCase(String titoloViaggio);
	
	List<Itinerario> findByTitoloViaggioContainingIgnoreCase(String titoloViaggio);
	
	@Query("SELECT d.itinerario FROM Destinazione d WHERE LOWER(d.nomeDestinazione) = LOWER(:nome)") 
	List<Itinerario> findByDestinazioneNome(@Param("nome") String nomeDestinazione);
	
	
	@Query("SELECT d.itinerario FROM Destinazione d "
			+ "WHERE LOWER(d.nomeDestinazione) = LOWER(CONCAT('%', :nome, '%'))") 
	List<Itinerario> findByDestinazioneWhereNomeDestinazioneLike(@Param("nome") String nomeDestinazione);
	
	
	List<Itinerario> findTop10ByOrderByLikesDesc();
	
	@Query("SELECT i.visibilita FROM Itinerario i WHERE i.id = :id")
	Optional<EnumVisibilita> findVisibilityById(@Param("id") Long id);
	

}
