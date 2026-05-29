package com.travelbuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelbuddy.entity.Giorno;
import com.travelbuddy.entity.Tappa;

public interface TappaRepository extends JpaRepository<Tappa, Long>{
	
	//dato il giorno, voglio vedere tutte le tappe che ho fatto quel giorno 
	List<Tappa> findByGiornoId(Long idgiorno); 
	
	//dato l'id della Tappa, vedo in che giorno o in che giorni l'ho fatta 
	//una tappa può essere stata fatta in diversi giorni più volte in uno stesso itinerario 
	@Query("SELECT t.giorno FROM Tappa t WHERE t.id = :idTappa")
	Optional<Giorno> findGiornoByTappaId(@Param("idTappa") Long IdTappa);
	
}
