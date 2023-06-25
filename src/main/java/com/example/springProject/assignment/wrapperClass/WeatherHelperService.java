package com.example.springProject.assignment.wrapperClass;

import com.example.springProject.assignment.dto.WeatherApiResponse;
import com.example.springProject.assignment.exception.WeatherServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherHelperService {
    @Value("${weather.api.key}") // API key from a weather service provider
    private String apiKey;
    private RestTemplate restTemplate = new RestTemplate();

    public WeatherApiResponse fetchWeatherDetails(String city, int quantity)
    {
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey;

        try {

            String response = restTemplate.getForObject(apiUrl, String.class);
        if (!response.isEmpty()) {
            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherApiResponse weatherApiResponse = objectMapper.readValue(response, WeatherApiResponse.class);

            // Process the weather forecast and apply additional conditions
            return weatherApiResponse;
        } else {
            // Handle error response
            throw new WeatherServiceException("Failed to retrieve weather forecast.");
        }
    } catch (Exception e) {
        throw new WeatherServiceException("Failed to retrieve weather forecast." + e.getMessage());
    }
    }



}
