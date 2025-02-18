package com.example.lab1;

// Импорт необходимых библиотек
import com.fasterxml.jackson.databind.ObjectMapper; // Для работы с JSON
import org.slf4j.Logger; // Для логирования
import org.slf4j.LoggerFactory; // Фабрика для создания логгеров
import org.springframework.beans.factory.annotation.Value; // Для внедрения значений из конфигурации
import org.springframework.web.bind.annotation.GetMapping; // Для обработки GET-запросов
import org.springframework.web.bind.annotation.RequestParam; // Для работы с параметрами запроса
import org.springframework.web.bind.annotation.RestController; // Указывает, что это контроллер REST
import org.springframework.web.client.RestTemplate; // Для отправки HTTP-запросов

import java.math.BigDecimal; // Для работы с большими десятичными числами
import java.math.RoundingMode; // Для указания режима округления
import java.util.HashMap; // Для использования хэш-таблицы
import java.util.Map; // Для работы с коллекциями

@RestController
public class SunriseSunsetController {

	private static final String API_URL = "https://api.sunrise-sunset.org/json";
	private static final Logger logger = LoggerFactory.getLogger(SunriseSunsetController.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${sunrise-sunset.latitude:51.3026}") // Координаты Санкт-Петербурга по умолчанию
	private double defaultLatitude;

	@Value("${sunrise-sunset.longitude:00.0739}") // Координаты Санкт-Петербурга по умолчанию
	private double defaultLongitude;

	private static final int ROUNDING_PLACES = 2;

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

		// Валидация координат
		if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
			return Map.of("error", "Invalid latitude or longitude");
		}

		latitude = round(latitude);
		longitude = round(longitude);

		logger.info("Received request for lat: {}, lng: {}, date: {}", latitude, longitude, date);
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format("%s?lat=%f&lng=%f&date=%s", API_URL, latitude, longitude, date != null ? date : "today");

		try {
			Map<String, Object> response = restTemplate.getForObject(url, Map.class);
			logger.info("Response from API:\n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));

			if (response != null && response.get("results") instanceof Map) {
				Map<String, Object> results = (Map<String, Object>) response.get("results");
				return results != null ? results : Map.of("error", "No results found");
			} else {
				return Map.of("error", "Unexpected response format");
			}
		} catch (Exception e) {
			logger.error("Error fetching data from API: {}", e.getMessage());
			return Map.of("error", "Unable to fetch data");
		}
	}

	// Метод для округления координат
	private double round(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(ROUNDING_PLACES, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}