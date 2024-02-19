
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
import ru.spbstu.tesseract.dto.CreateDiversificationRequestDto;
import ru.spbstu.tesseract.dto.DiversificationLongDto;
import ru.spbstu.tesseract.dto.DiversificationShortDto;
import ru.spbstu.tesseract.service.DiversificationService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DiversificationControllerTest {

    @Mock
    DiversificationService diversificationService;

    @InjectMocks
    DiversificationController diversificationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(diversificationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void givenDiversifications_whenGetDiversifications_thenStatusIsOk() throws Exception {
        given(diversificationService.getDiversifications(anyInt(), anyInt()))
                .willReturn(Arrays.asList(
                        DiversificationShortDto.builder()
                                .diversificationId(1)
                                .createDatetime("2022-01-01T12:00:00")
                                .riskTypeId(1)
                                .currentAmount(100L)
                                .amountDiff(1000L)
                                .build(),

                        DiversificationShortDto.builder()
                                .diversificationId(2)
                                .createDatetime("2022-02-01T12:00:00")
                                .riskTypeId(2)
                                .currentAmount(200L)
                                .amountDiff(2000L)
                                .build()
                ));



        mockMvc.perform(get("/api/v1/diversifications")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void givenDiversification_whenGetDiversification_thenStatusIsOkAndPayloadExists() throws Exception {
        given(diversificationService.getDiversification(anyInt()))
                .willReturn(DiversificationLongDto.builder()
                        .createDateTime("2022-01-01T12:00:00")
                        .currentAmount(100L)
                        .riskTypeId(10)
                        .assetList(Arrays.asList())
                        .amountDiff(1000L)
                        .build()
                );

        mockMvc.perform(get("/api/v1/diversifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(DiversificationLongDto.builder()
                                .createDateTime("2022-01-01T12:00:00")
                                .currentAmount(100L)
                                .riskTypeId(10)
                                .assetList(Arrays.asList())
                                .amountDiff(1000L)
                                .build()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void givenCreateDiversificationRequestDto_whenCreateDiversification_thenStatusIsOk() throws Exception {
        mockMvc.perform(post("/api/v1/diversifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new CreateDiversificationRequestDto())))
                .andExpect(status().isOk());
    }

}