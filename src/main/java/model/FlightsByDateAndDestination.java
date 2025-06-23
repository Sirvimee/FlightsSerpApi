package model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FlightsByDateAndDestination {
    private LocalDate date;
    private String arrivalId;
    private List<FlightResult> flights;
}