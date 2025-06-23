package com.example.FlightsFromSerpApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightsFromSerpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsFromSerpApiApplication.class, args);
	}

}
