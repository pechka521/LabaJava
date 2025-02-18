package com.example.lab1;

import org.junit.jupiter.api.Test; // Импорт аннотации для тестов JUnit 5
import org.springframework.boot.test.context.SpringBootTest; // Импорт аннотации для интеграционных тестов Spring Boot

// Указывает, что это класс теста, который будет использовать контекст Spring Boot
@SpringBootTest
class Demo1ApplicationTests {

	// Метод теста, который будет выполнен при запуске тестов
	@Test
	void contextLoads() {
		// Этот тест проверяет, что приложение загружается без ошибок
		// Если контекст приложения не загрузится, тест завершится неудачно
	}
}