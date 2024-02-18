package ru.spbstu.tesseract.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.service.FavouritesService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FavouritesControllerTest {

    @Mock
    FavouritesService favouritesService;

    @InjectMocks
    FavouritesController favouritesController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(favouritesController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void favourites() throws Exception {
        given(favouritesService.getFavouriteAssets(anyInt(), anyInt()))
                .willReturn(Arrays.asList(
                        AssetShortDto.builder().build(),
                        AssetShortDto.builder().build()
                ));

        mockMvc.perform(get("/api/v1/favourites")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void addFavourite() throws Exception {
        given(favouritesService.addFavourite(anyInt()))
                .willReturn(true);

        mockMvc.perform(post("/api/v1/favourites/1"))
                .andExpect(status().isCreated());
    }

    @Test
    void addFavourite_alreadyExisting() throws Exception {
        given(favouritesService.addFavourite(anyInt()))
                .willReturn(false);

        mockMvc.perform(post("/api/v1/favourites/1"))
                .andExpect(status().isOk());
    }

    @Test
    void removeFavourite() throws Exception {
        mockMvc.perform(delete("/api/v1/favourites/1"))
                .andExpect(status().isOk());
    }
}