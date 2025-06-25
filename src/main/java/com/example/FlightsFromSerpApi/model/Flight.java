package com.example.FlightsFromSerpApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String airline;
    private String destination;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "arrival_time")
    private Timestamp arrivalTime;

    private double price;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<FlightSeat> flightSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<ReservationSeat> reservationSeats;
}

