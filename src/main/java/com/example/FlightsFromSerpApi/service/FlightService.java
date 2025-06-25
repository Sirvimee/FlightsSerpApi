package com.example.FlightsFromSerpApi.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.FlightsFromSerpApi.model.Flight;
import com.example.FlightsFromSerpApi.model.FlightResult;
import com.example.FlightsFromSerpApi.model.FlightsByDateAndDestination;
import com.example.FlightsFromSerpApi.model.SerpApiProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.FlightsFromSerpApi.repository.FlightRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final WebClient.Builder webClientBuilder;
    private final FlightRepository flightRepository;
    private final SerpApiProperties props;

    private static final String DEPARTURE_ID = "TLL";
    private static final List<String> ARRIVAL_IDS = List.of("FRA", "HEL", "AYT");

    public List<FlightsByDateAndDestination> fetchFlightsForNextDays(int days) {
        List<FlightsByDateAndDestination> results = new ArrayList<>();
        LocalDate today = LocalDate.now().plusDays(5);

        for (int i = 0; i < days; i++) {
            LocalDate date = today.plusDays(i);
            for (String arrivalId : ARRIVAL_IDS) {
                List<FlightResult> flights = fetchFlightsForDateAndDestination(date, arrivalId);
                FlightsByDateAndDestination dto = new FlightsByDateAndDestination();
                dto.setDate(date);
                dto.setArrivalId(arrivalId);
                dto.setFlights(flights);
                results.add(dto);
            }
        }

        return results;
    }

    private List<FlightResult> fetchFlightsForDateAndDestination(LocalDate date, String arrivalId) {
        WebClient client = webClientBuilder
                .baseUrl(props.getBaseUrl())
                .build();

        JsonNode root = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.json")
                        .queryParam("engine", "google_flights")
                        .queryParam("departure_id", DEPARTURE_ID)
                        .queryParam("arrival_id", arrivalId)
                        .queryParam("outbound_date", date.toString())
                        .queryParam("type", "2")
                        .queryParam("api_key", props.getApiKey())
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        List<FlightResult> results = new ArrayList<>();
        JsonNode bestFlights = root.path("best_flights");

        if (bestFlights.isArray()) {
            for (JsonNode offer : bestFlights) {
                JsonNode segments = offer.path("flights");
                if (segments.isArray()) {
                    for (JsonNode flightNode : segments) {
                        FlightResult flight = new FlightResult();
                        flight.setAirline(flightNode.path("airline").asText());

                        flight.setDepartureAirport(flightNode.path("departure_airport").path("name").asText());
                        flight.setArrivalAirport(flightNode.path("arrival_airport").path("name").asText());
                        flight.setDepartureTime(flightNode.path("departure_airport").path("time").asText());
                        flight.setArrivalTime(flightNode.path("arrival_airport").path("time").asText());

                        results.add(flight);
                    }
                }
            }
        }
        return results;
    }

    @Transactional
    public void deleteFlightsByDate(LocalDate date) {
        List<Flight> flights = flightRepository.findAllByDate(date);
        for (Flight f : flights) {
            flightRepository.delete(f);
        }
    }

}