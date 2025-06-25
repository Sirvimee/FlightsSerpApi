package com.example.FlightsFromSerpApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.FlightsFromSerpApi.model.Flight;
import com.example.FlightsFromSerpApi.model.FlightResult;
import com.example.FlightsFromSerpApi.model.FlightSeat;
import com.example.FlightsFromSerpApi.model.FlightsByDateAndDestination;
import org.springframework.stereotype.Service;
import com.example.FlightsFromSerpApi.repository.FlightRepository;
import com.example.FlightsFromSerpApi.repository.FlightSeatRepository;
import com.example.FlightsFromSerpApi.repository.SeatRepository;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightImportScheduler {

  private final FlightService flightService;
  private final FlightRepository flightRepository;
  private final SeatRepository seatRepository;
  private final FlightSeatRepository flightSeatRepository;
  private final Random random = new Random();

  public void importTomorrowsFlights() {
    log.info("----- Alustan lennuandmete importi -----");

    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate today = LocalDate.now();
    flightService.deleteFlightsByDate(yesterday);
    flightService.deleteFlightsByDate(today);

    for (FlightsByDateAndDestination dto : flightService.fetchFlightsForNextDays(1)) {
      for (FlightResult fr : dto.getFlights()) {
        Flight flight = mapToEntity(fr, dto.getDate());
        flightRepository.save(flight);

        createFlightSeatsForFlight(flight);
      }
    }

    log.info("----- Import valmis -----");
  }

  private void createFlightSeatsForFlight(Flight flight) {
    var seats = seatRepository.findAll();

    var flightSeats = seats.stream()
            .map(seat -> {
              FlightSeat fs = new FlightSeat();
              fs.setFlight(flight);
              fs.setSeat(seat);
              fs.setAvailable(random.nextBoolean()); // suvaline saadavus
              return fs;
            })
            .toList();

    flightSeatRepository.saveAll(flightSeats);
    log.info("Lennule id={} lisatud {} istekohaga FlightSeat-id", flight.getId(), flightSeats.size());
  }

  private Flight mapToEntity(FlightResult fr, LocalDate flightDate) {
    Flight f = new Flight();
    f.setDate(flightDate);
    f.setAirline(fr.getAirline());
    f.setDestination(fr.getArrivalAirport());

    // parsi "2025-06-29 06:00" kujulised stringid
    DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    LocalDateTime departure = LocalDateTime.parse(fr.getDepartureTime(), fullFormatter);
    f.setDepartureTime(departure.toLocalTime());

    LocalDateTime arrival = LocalDateTime.parse(fr.getArrivalTime(), fullFormatter);
    f.setArrivalTime(Timestamp.valueOf(arrival));

    f.setPrice(100 + random.nextInt(401)); // hind vahemikus 100–500
    f.setAirplaneId(random.nextBoolean() ? 1L : 2L); // 1 või 2

    return f;
  }
}
