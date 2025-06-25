package com.example.FlightsFromSerpApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import service.FlightImportScheduler;

@SpringBootApplication
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
      flightImportScheduler.importTomorrowsFlights();
      System.exit(0); 
    }
  }
}
