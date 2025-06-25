package com.example.FlightsFromSerpApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.FlightsFromSerpApi.service.FlightImportScheduler;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlightsFromSerpApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlightsFromSerpApiApplication.class, args);
        try {
            FlightImportScheduler scheduler = context.getBean(FlightImportScheduler.class);
            scheduler.importTomorrowsFlights();
        } catch (Exception e) {
            System.err.println(" Import ebaõnnestus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }
}
