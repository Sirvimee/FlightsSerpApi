package com.example.FlightsFromSerpApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
