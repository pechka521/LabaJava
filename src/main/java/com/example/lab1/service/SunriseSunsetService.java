package com.example.lab1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SunriseSunsetService {

    private static final String API_URL = "https://api.sunrise-sunset.org/json";

    public Map<String, Object> getSunriseSunset(double latitude, double longitude, String date) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?lat=%f&lng=%f&date=%s", API_URL, latitude, longitude, date != null ? date : "today");
        return restTemplate.getForObject(url, Map.class);
    }
}