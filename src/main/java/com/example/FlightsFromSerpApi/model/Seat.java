package com.example.FlightsFromSerpApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_class")
    private SeatClass seatClass;

    @Column(name = "extra_legroom")
    private boolean extraLegroom;

    @Column(name = "exit_row")
    private boolean exitRow;

    @JsonIgnore
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<AirplaneSeat> airplaneSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<FlightSeat> flightSeats;
}

