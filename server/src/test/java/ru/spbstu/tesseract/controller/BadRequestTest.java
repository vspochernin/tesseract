package ru.spbstu.tesseract.controller;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class BadRequestTest {

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

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/assets",
            "/api/v1/diversifications",
            "/api/v1/favourites"})
    @DisplayName("Некорректные query параметры GET запроса")
    public void givenInvalidQueryParams_whenHandleGetRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(get(path)
                        .param("pageNumber", "123qwe123")
                        .param("pageSize", "123qwe123")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.errorType").value("BAD_REQUEST_BODY"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/assets/",
            "/api/v1/diversifications/"})
    @DisplayName("Некорректные параметры пути GET запроса")
    public void givenIncorrectPathParams_whenHandleGetRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(get(path + "123qwe123")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.errorType").value("BAD_REQUEST_BODY"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/favourites/"})
    @DisplayName("Некорректные параметры пути POST запроса")
    public void givenIncorrectPathParams_whenHandlePostRequest_thenReturnsExpectedError(String path) throws Exception {
        mockMvc.perform(post(path + "123qwe123")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.errorType").value("BAD_REQUEST_BODY"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/favourites/"})
    @DisplayName("Некорректные параметры пути DELETE запроса")
    public void givenIncorrectPathParams_whenHandleDeleteRequest_thenReturnsExpectedError(String path) throws
            Exception
    {
        mockMvc.perform(delete(path + "123qwe123")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.errorType").value("BAD_REQUEST_BODY"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/register",
            "/api/v1/login/tesseract",
            "/api/v1/login/google",
            "/api/v1/diversifications"})
    @DisplayName("Некорректные параметры тела POST запроса")
    public void givenIncorrectBodyParams_whenHandlePostRequest_thenReturnsExpectedError(String path) throws Exception {
        HashMap<String, String> registrationRequest = new HashMap<>();
        registrationRequest.put("incorrect", "body");

        mockMvc.perform(post(path)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.errorType").value("BAD_REQUEST_BODY"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/api/v1/password"})
    @DisplayName("Некорректные параметры тела PUT запроса")
    public void givenIncorrectBodyParams_whenHandlePutRequest_thenReturnsExpectedError(String path) throws Exception {
        HashMap<String, String> registrationRequest = new HashMap<>();
        registrationRequest.put("incorrect", "body");

        mockMvc.perform(put(path)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.errorType").value("BAD_REQUEST_BODY"));
    }
}
