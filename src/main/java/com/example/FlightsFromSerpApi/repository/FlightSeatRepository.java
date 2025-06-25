package com.example.FlightsFromSerpApi.repository;

import com.example.FlightsFromSerpApi.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
}
