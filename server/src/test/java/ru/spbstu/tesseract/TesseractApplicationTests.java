package ru.spbstu.tesseract;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class TesseractApplicationTests {

	@Container
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.7")
			.withDatabaseName("tesseract")
			.withUsername("postgres")
			.withPassword("postgres");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.password", postgres::getPassword);
		registry.add("spring.datasource.username", postgres::getUsername);
	}


	@Test
	void contextLoads() {
	}

}
