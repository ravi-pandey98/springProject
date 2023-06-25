package com.example.springProject.assignment.wrapperClass;

import com.example.springProject.assignment.common.ApiCallResp;
import com.example.springProject.assignment.dto.WeatherApiResponse;
import com.example.springProject.assignment.exception.WeatherServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
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
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
         ApiCallResp apiCallResp = getApiCall(apiUrl, headers);
         if(apiCallResp.getHttpStatus().is2xxSuccessful()) {
             ObjectMapper objectMapper = new ObjectMapper();
             try {
                 WeatherApiResponse weatherApiResponse = objectMapper.readValue(apiCallResp.getBody(), WeatherApiResponse.class);
                 return weatherApiResponse;
             } catch (Exception e) {
                 throw new WeatherServiceException("Failed to parse data");
             }
         }
         else
         {
             throw new WeatherServiceException(apiCallResp.getBody());
         }


    }

    private ApiCallResp getApiCall(String url, HttpHeaders httpHeaders)
    {
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ApiCallResp apiCallResp;
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            apiCallResp = new ApiCallResp(response.getBody(), (HttpStatus) response.getStatusCode());
        }
        catch (HttpClientErrorException | HttpServerErrorException ex)
        {
            apiCallResp = new ApiCallResp(ex.getResponseBodyAsString(), (HttpStatus) ex.getStatusCode());
        }
        catch (ResourceAccessException ex)
        {
            apiCallResp = new ApiCallResp(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return apiCallResp;
    }



}
