package com.example.springProject.assignment.service.impl;


import com.example.springProject.assignment.dto.*;
import com.example.springProject.assignment.service.WeatherService;
import com.example.springProject.assignment.wrapperClass.WeatherHelperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class WeatherServiceImpl implements WeatherService {


    @Autowired
    private WeatherHelperService weatherHelperService;

    private Map<String, WeatherForecastResponse> cacheData = new HashMap<>();

    @Override
    public WeatherForecastResponse getWeather(String city) {

        if(cacheData.containsKey(city))
        {
            log.info("Data found in map");
            return cacheData.get(city);
        }
        else {
            log.info("Calling weather api to fetch data");
            WeatherApiResponse weatherResponse = weatherHelperService.fetchWeatherDetails(city, 10);
            WeatherForecastResponse weatherForecastResponse = processWeatherForecast(weatherResponse, city);
            log.info("Saving data in cache");
            cacheData.put(city, weatherForecastResponse);
            return weatherForecastResponse;
        }

    }

    @Override
    public Object clearCacheData() {
        cacheData.clear();
        return "Data cleared";
    }

    /**
     * This mehtod is basically processing weatherApiResponse data
     * @param weatherApiResponse
     * @param city
     * @return WeatherForecastResponse
     */

    private WeatherForecastResponse processWeatherForecast(WeatherApiResponse weatherApiResponse, String city) {
        // Extract relevant data from the API response and apply additional conditions
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setCity(city);

        String timeStamp = getNextDate();

        List<DayForecast> dayForecasts = new ArrayList<>();
        for (WeatherDetail weatherDetail : weatherApiResponse.getList()) {
            if(weatherDetail.getDt_txt().substring(0,10).contains(timeStamp))
            {
                break;
            }
            DayForecast dayForecast = new DayForecast();
            dayForecast.setDate(weatherDetail.getDt_txt());
            dayForecast.setTemp(weatherDetail.getMain().getTemp());
            dayForecast.setHighTemp(weatherDetail.getMain().getTemp_max());
            dayForecast.setLowTemp(weatherDetail.getMain().getTemp_min());


            // Additional conditions
            if (weatherDetail.getWeather().get(0).getMain().contains("Rain")) {
                dayForecast.setPrediction("Carry umbrella");
            } else if (weatherDetail.getWeather().get(0).getMain().contains("Thunderstorm")) {
                dayForecast.setPrediction("Don't step out! A Storm is brewing!");
            }

            if (weatherDetail.getMain().getTemp() > 104) {
                if(dayForecast.getPrediction() == null)
                {
                    dayForecast.setPrediction("Temp is more than 40 degree celisus today please Use sunscreen lotion");
                }
                else {
                    dayForecast.getPrediction().concat("Temp is more than 40 degree celisus today please Use sunscreen lotion");
                }
            }

            if (weatherDetail.getWind().getSpeed() > 10) {
                if(dayForecast.getPrediction() == null)
                {
                    dayForecast.setPrediction("It's too windy, watch out!");
                }
                else {
                    dayForecast.getPrediction().concat("It's too windy, watch out!");
                }

            }
            if(dayForecast.getPrediction() == null)
            {
                dayForecast.setPrediction(weatherDetail.getWeather().get(0).getDescription());
            }

            dayForecasts.add(dayForecast);
        }

        weatherForecastResponse.setWeatherForecastList(dayForecasts);

        return weatherForecastResponse;
    }

    /**
     * Calculating next date
     * @return
     */

    public static String getNextDate() {
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(curDate);
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, 3);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            log.error("Unable to fetch get next date");
        }
        return nextDate;
    }
}
