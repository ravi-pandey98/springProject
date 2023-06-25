package com.example.springProject.assignment.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DayForecast {

    private double temp;

    private String date;

    private double highTemp;

    private double lowTemp;

    private String prediction;
}
