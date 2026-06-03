package com.travelbuddy.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.travelbuddy.listaenum.EnumRuolo;

//importazione jUnit
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtenteRepositoryTest {

	@Autowired
	private UtenteRepository utenteRepository;
	
//	@Query("SELECT u.ruolo FROM Utente u WHERE u.id = :id")
//	Optional<EnumRuolo> findRoleById(@Param("id") Long id);
	
	@Test
	void findRoleByIdEqualsUser() {
		Long id = 1L;

		Optional<EnumRuolo> ruolo = utenteRepository.findRoleById(id);

		assertTrue(ruolo.isPresent());
		assertEquals(EnumRuolo.USER, ruolo.get());
	}
	
	@Test
	void findRoleByIdNotEqualsUser() {
		Long id = 1L;

		Optional<EnumRuolo> ruolo = utenteRepository.findRoleById(id);

		assertTrue(ruolo.isPresent());
		assertNotEquals(EnumRuolo.ADMIN, ruolo.get());
	}
	
	
	
//	
//	@Query("SELECT u.status FROM Utente u WHERE u.id = :id")
//	Optional<EnumStatus> findStatusById(@Param("id") Long id);
	
	
	
	
}


