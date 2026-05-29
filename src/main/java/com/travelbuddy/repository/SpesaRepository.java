package com.travelbuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelbuddy.entity.Spesa;

public interface SpesaRepository extends JpaRepository<Spesa, Long>{
	
}
