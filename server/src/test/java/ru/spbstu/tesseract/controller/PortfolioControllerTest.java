package ru.spbstu.tesseract.controller;

import java.util.HashMap;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class PortfolioControllerTest {

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
                .locations("classpath:integration/migration")
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Получить первые 10 своих портфелей")
    public void givenCorrectRequest_whenGetPortfolios_thenReturnsPortfoliosWithExpectedIds() throws
            Exception
    {

        mockMvc.perform(get("/api/v1/portfolios")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$[0].portfolioId").value(2));
    }

    @Test
    @DisplayName("Получить свой существующий портфель c id = 2")
    public void givenPortfolioId2_whenGetPortfolio_thenReturnsExpectedPortfolio() throws Exception {
        mockMvc.perform(get("/api/v1/portfolios/2")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createDateTime").value("2023-12-27T21:00Z"))
                .andExpect(jsonPath("$.currentAmount").value(3310000))
                .andExpect(jsonPath("$.riskTypeId").value(2))
                .andExpect(jsonPath("$.assetList[*].assetId", Matchers.containsInAnyOrder(1, 4, 7)))
                .andExpect(jsonPath("$.assetList[*].assetTitle",
                        Matchers.containsInAnyOrder("NDM_18", "NDM_13", "NDM_53")))
                .andExpect(jsonPath("$.amountDiff").value(-11690000));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 1001})
    @DisplayName("Получить не свой или несуществующий")
    public void givenNonExistentOrNonYourPortfolio_whenGetPortfolio_thenReturnsExpectedError(int id) throws
            Exception
    {
        mockMvc.perform(get("/api/v1/portfolios/" + id)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"));

    }

    private void checkIfPortfolioWithId3IsNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/portfolios/3")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"));
    }

    private static Stream<Arguments> validCreatePortfolioRequestArgumentsProvider() {
        return Stream.of(
                Arguments.of(170_00L, 3),
                Arguments.of(5_000_000_00L, 3),
                Arguments.of(10_000_000_00L, 3),
                Arguments.of(5_000_000_00L, 0),
                Arguments.of(5_000_000_00L, 1),
                Arguments.of(5_000_000_00L, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("validCreatePortfolioRequestArgumentsProvider")
    @DisplayName("Создание портфеля с корректными данными")
    public void givenValidCreatePortfolioRequest_whenCreatePortfolio_thenSuccess(
            Long amount,
            Integer riskTypeId) throws Exception
    {
        HashMap<String, Object> request = new HashMap<>();
        request.put("amount", amount);
        request.put("riskTypeId", riskTypeId);

        checkIfPortfolioWithId3IsNotExists();

        mockMvc.perform(post("/api/v1/portfolios")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/portfolios/3")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentAmount").value(lessThanOrEqualTo(amount.intValue())))
                .andExpect(jsonPath("$.riskTypeId").value(riskTypeId));
    }

    private static Stream<Arguments> invalidCreatePortfolioRequestArgumentsProvider() {
        return Stream.of(
                Arguments.of(169_99L, 3),
                Arguments.of(10_000_000_01L, 3),
                Arguments.of(5_000_000_00L, -1),
                Arguments.of(5_000_000_00L, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCreatePortfolioRequestArgumentsProvider")
    @DisplayName("Создание портфеля с некорректными данными")
    public void givenInvalidCreatePortfolioRequest_whenCreatePortfolio_thenReturnsExpectedError(
            Long amount,
            Integer riskTypeId) throws Exception
    {
        HashMap<String, Object> request = new HashMap<>();
        request.put("amount", amount);
        request.put("riskTypeId", riskTypeId);

        checkIfPortfolioWithId3IsNotExists();

        mockMvc.perform(post("/api/v1/portfolios")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        checkIfPortfolioWithId3IsNotExists();
    }
}
