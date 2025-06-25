package com.example.FlightsFromSerpApi.repository;

import com.example.FlightsFromSerpApi.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
}
