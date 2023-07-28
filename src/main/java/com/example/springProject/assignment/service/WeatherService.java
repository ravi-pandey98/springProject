package com.example.springProject.assignment.service;

import com.example.springProject.assignment.dto.Weather;
import com.example.springProject.assignment.dto.WeatherForecastResponse;

import java.util.List;

public interface WeatherService {
    WeatherForecastResponse getWeather(String city);

    Object clearCacheData();
}
