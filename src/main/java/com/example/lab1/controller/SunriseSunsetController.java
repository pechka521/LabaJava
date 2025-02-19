package com.example.lab1.controller;

import com.example.lab1.service.SunriseSunsetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SunriseSunsetController {

	private static final Logger logger = LoggerFactory.getLogger(SunriseSunsetController.class);
	private final SunriseSunsetService sunriseSunsetService;

	@Value("${sunrise-sunset.latitude:51.3026}")
	private double defaultLatitude;

	@Value("${sunrise-sunset.longitude:00.0739}")
	private double defaultLongitude;

	public SunriseSunsetController(SunriseSunsetService sunriseSunsetService) {
		this.sunriseSunsetService = sunriseSunsetService;
	}

	@GetMapping("/sunrise-sunset")
	public Map<String, Object> getSunriseSunset(
			@RequestParam(required = false) Double latitude,
			@RequestParam(required = false) Double longitude,
			@RequestParam(required = false) String date) {

		if (latitude == null) {
			latitude = defaultLatitude;
		}
		if (longitude == null) {
			longitude = defaultLongitude;
		}

		logger.info("Received request for lat: {}, lng: {}, date: {}", latitude, longitude, date);
		return sunriseSunsetService.getSunriseSunset(latitude, longitude, date);
	}
}