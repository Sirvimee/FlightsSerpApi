package com.example.FlightsFromSerpApi.model;

import lombok.Data;

@Data
public class FlightResult {
    private String airline;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
}