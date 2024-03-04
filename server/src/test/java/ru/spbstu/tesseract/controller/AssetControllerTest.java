package ru.spbstu.tesseract.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class AssetControllerTest {

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

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("Получить первые 10 активов, вторые 10 активов")
    public void givenPageNumber_whenGetAssets_thenReturnsAssetsWithExpectedIds(int pageNumber) throws Exception {
        int startId = pageNumber * 10 + 1;
        int endId = startId + 9;

        mockMvc.perform(get("/api/v1/assets")
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", "10")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(10))
                .andDo(result -> {
                    for (int expectedId = startId, i = 0; expectedId <= endId; expectedId++, i++) {
                        jsonPath(String.format("$[%d].assetId", i)).value(expectedId);
                    }
                });
    }

    @Test
    @DisplayName("Получить существующий актив c id = 10")
    public void givenAssetId10_whenGetAsset_thenReturnsExpectedAsset() throws Exception {
        mockMvc.perform(get("/api/v1/assets/10")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetId").value(10))
                .andExpect(jsonPath("$.assetTitle").value("NDM_5"))
                .andExpect(jsonPath("$.assetDescription").value(
                        "Количество выпускаемых ЦФА: 30 000 штук. Дробление ЦФА и досрочное погашение не " +
                                "предусмотрено. Каждый ЦФА имеет фиксированную номинальную стоимость в размере 5000 " +
                                "рублей Российской Федерации. Выплаты производятся ежемесячно"))
                .andExpect(jsonPath("$.assetPrice").value(495000))
                .andExpect(jsonPath("$.assetPriceDiff").value(0))
                .andExpect(jsonPath("$.companyTitle").value(
                        "Общество ограниченной ответственностью «Глобал Факторинг Нетворк Рус»"))
                .andExpect(jsonPath("$.companyDescription").value(
                        "Федеральная факторинговая компания, занимающаяся оперативным финансированием оборотного " +
                                "капитала предприятий под уступку надежной краткосрочной дебиторской задолженности с " +
                                "использованием юридически значимого электронного документооборота. Компания входит в" +
                                " топ-30 факторинговых компаний России, в топ-10 по сделкам с предприятиями малого и " +
                                "среднего бизнеса"))
                .andExpect(jsonPath("$.riskTypeId").value(1))
                .andExpect(jsonPath("$.favouriteStatus").value(false))
                .andExpect(jsonPath("$.operatorTitle").value(
                        "Общество с ограниченной ответственностью \"Атомайз\", ООО \"Атомайз\""));
    }

    @Test
    @DisplayName("Получить несуществующий актив")
    public void givenNonExistent_whenGetAsset_thenReturnsExpectedError() throws Exception {
        mockMvc.perform(get("/api/v1/assets/1001")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"));
    }
}
