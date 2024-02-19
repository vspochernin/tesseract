package ru.spbstu.tesseract.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.spbstu.tesseract.dto.AssetLongDto;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.service.AssetService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AssetControllerTest {

    @Mock
    AssetService assetService;

    @InjectMocks
    AssetController assetController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(assetController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void assets() throws Exception {
        given(assetService.getAssets(anyInt(), anyInt()))
                .willReturn(Arrays.asList(
                        AssetShortDto.builder().build(),
                        AssetShortDto.builder().build()
                ));

        mockMvc.perform(get("/api/v1/assets")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void asset() throws Exception {
        given(assetService.getAsset(anyInt()))
                .willReturn(AssetLongDto.builder().build());

        mockMvc.perform(get("/api/v1/assets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }
}