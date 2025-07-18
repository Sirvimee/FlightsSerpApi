package com.example.FlightsFromSerpApi.repository;

import com.example.FlightsFromSerpApi.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
