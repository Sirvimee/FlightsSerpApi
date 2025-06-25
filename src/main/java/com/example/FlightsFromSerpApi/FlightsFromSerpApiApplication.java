package com.example.FlightsFromSerpApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import service.FlightImportScheduler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.FlightsFromSerpApi", "service", "repository", "model"})
@EnableJpaRepositories(basePackages = {"repository"})
public class FlightsFromSerpApiApplication implements CommandLineRunner {

    @Autowired
    private FlightImportScheduler flightImportScheduler;

    @Autowired
    private ApplicationArguments args;

    public static void main(String[] args) {
        SpringApplication.run(FlightsFromSerpApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (this.args.containsOption("importFlights")) {
            try {
                flightImportScheduler.importTomorrowsFlights();
                System.exit(0); // edukas töö lõpp Railway jaoks
            } catch (Exception e) {
                System.err.println("Lennundusandmete import ebaõnnestus: " + e.getMessage());
                e.printStackTrace();
                System.exit(1); // Railway saab veast teada
            }
        }
    }
}
