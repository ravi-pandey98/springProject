package com.example.springProject.assignment.controller;


import com.example.springProject.assignment.dto.WeatherForecastResponse;
import com.example.springProject.assignment.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;



    @ApiOperation(value = "Fetching Weather details by city", notes = "This api is basically responsible for fetch last 3 Weather records for a given city", httpMethod = "GET")
    @ApiResponse(code = 200, message = "List of temperatures along with date")
    @GetMapping("/{city}")
    public ResponseEntity<WeatherForecastResponse> getWeather(@PathVariable String city) {
        if(city == null || city.isBlank())
        {
            throw new RuntimeException("city can't be null or empty");
        }
        return new ResponseEntity<>(weatherService.getWeather(city), HttpStatus.ACCEPTED);
    }

    @GetMapping("/clearCache")
    public ResponseEntity clearCacheData()
    {
        return new ResponseEntity<>(weatherService.clearCacheData(), HttpStatus.ACCEPTED);
    }
}
