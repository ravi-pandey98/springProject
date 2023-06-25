package com.example.springProject.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeatherServiceException extends RuntimeException{
    public WeatherServiceException(String message)
    {
        super(message);
    }

}
