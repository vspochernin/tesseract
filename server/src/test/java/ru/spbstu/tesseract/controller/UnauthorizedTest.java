package ru.spbstu.tesseract.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.spbstu.tesseract.utils.Secrets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class UnauthorizedTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.7")
            .withDatabaseName("tesseract")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
    }

    @BeforeEach
    void setup() {
        Flyway flyway = Flyway.configure()
                .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();
    }

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/assets",
            "/api/v1/diversifications",
            "/api/v1/favourites",
            "/api/v1/assets/",
            "/api/v1/diversifications/"})
    @DisplayName("Некорректные параметры аутентификации GET запроса")
    public void givenBadCredentials_whenHandleGetRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(get(path)
                        .header("Authorization", Secrets.INVALID_BEREAR_TOKEN))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/favourites/",
            "/api/v1/diversifications"})
    @DisplayName("Некорректные параметры аутентификации POST запроса")
    public void givenBadCredentials_whenHandlePostRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(post(path)
                        .header("Authorization", Secrets.INVALID_BEREAR_TOKEN))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/password"})
    @DisplayName("Некорректные параметры аутентификации PUT запроса")
    public void givenBadCredentials_whenHandlePutRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(put(path)
                        .header("Authorization", Secrets.INVALID_BEREAR_TOKEN))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/favourites/"})
    @DisplayName("Некорректные параметры аутентификации DELETE запроса")
    public void givenBadCredentials_whenHandleDeleteRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(delete(path)
                        .header("Authorization", Secrets.INVALID_BEREAR_TOKEN))
                .andExpect(status().isUnauthorized());
    }
}
