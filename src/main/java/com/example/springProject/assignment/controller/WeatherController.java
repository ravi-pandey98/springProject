package com.example.springProject.assignment.controller;


import com.example.springProject.assignment.dto.Weather;
import com.example.springProject.assignment.dto.WeatherForecastResponse;
import com.example.springProject.assignment.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<WeatherForecastResponse> getWeather(@PathVariable String city) {
        if(city == null || city.isBlank())
        {
            throw new RuntimeException("city can't be null or empty");
        }
        return new ResponseEntity<>(weatherService.getWeather(city), HttpStatus.ACCEPTED);
    }
}
