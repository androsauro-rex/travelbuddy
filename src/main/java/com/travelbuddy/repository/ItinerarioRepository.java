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
	
	
//	@Query("SELECT u FROM Utente u JOIN Diario d ON d.utente = u WHERE d.id = :idDiario")
//	Optional<Utente> findUtenteByDiarioId(@Param("idDiario") Long idDiario);
	
	//dato l'id di un diario, vogliamo trovare la lsita degli itinerari in esso presenti 
	//in un diario possono essere caricati zero itinerari, per cui mettiamo Optional 
	@Query("SELECT i FROM Itinerario i JOIN Diario d ON d.itinerario = i WHERE d.id = :idDiario")
	List<Itinerario> findItinerarioByDiarioId(@Param("idDiario") Long idDiario); 

}
