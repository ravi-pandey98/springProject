package com.example.springProject.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDetail implements Serializable {

    private Main main;

    List<Weather> weather = new ArrayList<>();

    private Wind wind;

    private String dt_txt;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind implements Serializable{
        private Integer speed;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main implements Serializable {
        private double temp;

        private double temp_min;

        private double temp_max;
    }

}
