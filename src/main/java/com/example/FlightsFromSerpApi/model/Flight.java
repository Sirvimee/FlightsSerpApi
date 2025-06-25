package com.example.FlightsFromSerpApi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String airline, destination;
    private LocalTime departureTime;
    private Timestamp arrivalTime;
    private double price;
    private Long airplaneId;
}

