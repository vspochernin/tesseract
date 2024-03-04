package ru.spbstu.tesseract.controller;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class FavouritesControllerTest {

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

    @Test
    @DisplayName("Получить первые 10 своих избранных активов")
    public void givenCorrectRequest_whenGetFavourites_thenReturnsFavouritesWithExpectedIdsAndStatus() throws Exception {
        checkFavouritesInitial();
    }

    @Test
    @DisplayName("Добавить существующий актив в избранное")
    public void givenExistsAssetId_whenAddFavourites_thenSuccess() throws Exception {
        checkFavouritesInitial();
        addExistsAssetInFavouritesFirstTime();
        checkFavouritesAdd();
        addExistsAssetInFavouritesSecondTime();
        checkFavouritesAdd();
    }

    @Test
    @DisplayName("Удалить существующий актив из избранного")
    public void givenExistsAssetId_whenRemoveFavourites_thenSuccess() throws Exception {
        checkFavouritesInitial();
        deleteExistsAssetFromFavourites();
        checkFavouritesInitial();
        addExistsAssetInFavouritesFirstTime();
        checkFavouritesAdd();
        deleteExistsAssetFromFavourites();
        checkFavouritesInitial();
    }

    @Test
    @DisplayName("Добавить несуществующий актив в избранное")
    public void givenNonExistentAssetId_whenAddFavourites_thenSuccess() throws Exception {
        checkFavouritesInitial();
        addNonExistentAssetInFavourites();
        checkFavouritesInitial();
    }

    @Test
    @DisplayName("Удалить несуществующий актив из избранного")
    public void givenNonExistentAssetId_whenRemoveFavourites_thenSuccess() throws Exception {
        checkFavouritesInitial();
        deleteNonExistentAssetFromFavourites();
        checkFavouritesInitial();
    }

    private void addExistsAssetInFavouritesSecondTime() throws Exception {
        mockMvc.perform(post("/api/v1/favourites/" + 10)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk());
    }

    private void addExistsAssetInFavouritesFirstTime() throws Exception {
        mockMvc.perform(post("/api/v1/favourites/" + 10)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isCreated());
    }

    private void deleteExistsAssetFromFavourites() throws Exception {
        mockMvc.perform(delete("/api/v1/favourites/" + 10)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk());
    }

    private void addNonExistentAssetInFavourites() throws Exception {
        mockMvc.perform(post("/api/v1/favourites/" + 1001)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isNotFound());
    }

    private void deleteNonExistentAssetFromFavourites() throws Exception {
        mockMvc.perform(delete("/api/v1/favourites/" + 1001)
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isNotFound());
    }

    private void checkFavouritesAdd() throws Exception {
        mockMvc.perform(get("/api/v1/favourites")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(5))
                .andExpect(jsonPath("$[*].assetId", Matchers.containsInAnyOrder(1, 2, 5, 8, 10)));
    }

    private void checkFavouritesInitial() throws Exception {
        mockMvc.perform(get("/api/v1/favourites")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .header("Authorization", Secrets.VRAZUKRANTOV_BEREAR_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(4))
                .andExpect(jsonPath("$[*].assetId", Matchers.containsInAnyOrder(1, 2, 5, 8)));
    }
}
