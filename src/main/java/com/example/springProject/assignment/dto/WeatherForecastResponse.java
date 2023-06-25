package com.example.springProject.assignment.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class WeatherForecastResponse {

    List<DayForecast> weatherForecastList = new ArrayList<>();


    private String city;
}
