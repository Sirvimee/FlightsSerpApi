package com.example.FlightsFromSerpApi.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="serpapi")
@Data
public class SerpApiProperties {
  private String apiKey, baseUrl;
}
