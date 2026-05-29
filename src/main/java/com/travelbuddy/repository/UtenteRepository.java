package com.travelbuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelbuddy.entity.Utente;
import com.travelbuddy.listaenum.EnumRuolo;
import com.travelbuddy.listaenum.EnumStatus;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	List<Utente> findByNomeIgnoreCase(String nome);
	
	List<Utente> findByCognomeIgnoreCase(String cognome);
	
	Optional<Utente> findByNicknameIgnoreCase(String nickname);
	
	Optional<Utente> findByEmail(String email);
	
	List<Utente> findByCognomeStartingWithIgnoreCase(String cognome);
	
	@Query("SELECT u.ruolo FROM Utente u WHERE u.id = :id")
	Optional<EnumRuolo> findRoleById(@Param("id") Long id);
	
	@Query("SELECT u.status FROM Utente u WHERE u.id = :id")
	Optional<EnumStatus> findStatusById(@Param("id") Long id);
	
	/*
	 * * Controlla se esiste almeno un record tale che ha lo stesso nick/email e ha un id diverso
	 * In tal caso non si procede con l'operazione di update
	 */
	boolean existsByNicknameAndIdNot(String nickname, Long id);
	
	boolean existsByEmailAndIdNot(String email, Long id);
	
	

}
