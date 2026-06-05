package com.travelbuddy.repository;


import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.travelbuddy.listaenum.EnumRoles;

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

		Optional<EnumRoles> ruolo = utenteRepository.findRoleById(id);

		assertTrue(ruolo.isPresent());
		assertEquals(EnumRoles.ROLE_USER, ruolo.get());
	}
	
	@Test
	void findRoleByIdNotEqualsUser() {
		Long id = 1L;

		Optional<EnumRoles> ruolo = utenteRepository.findRoleById(id);

		assertTrue(ruolo.isPresent());
		assertNotEquals(EnumRoles.ROLE_ADMIN, ruolo.get());
	}
	
	
	
//	
//	@Query("SELECT u.status FROM Utente u WHERE u.id = :id")
//	Optional<EnumStatus> findStatusById(@Param("id") Long id);
	
	
	
	
}


