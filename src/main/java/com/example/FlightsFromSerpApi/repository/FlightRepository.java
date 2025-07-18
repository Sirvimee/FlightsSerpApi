package com.example.FlightsFromSerpApi.repository;

import com.example.FlightsFromSerpApi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByDate(LocalDate date);
}
