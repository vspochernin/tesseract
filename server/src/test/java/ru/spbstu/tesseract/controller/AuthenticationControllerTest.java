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
import ru.spbstu.tesseract.dto.*;
import ru.spbstu.tesseract.service.AuthenticationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    AuthenticationService service;

    @InjectMocks
    AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register() throws Exception {
        given(service.register(any(RegisterRequestDto.class)))
                .willReturn(new AuthenticationResponseDto());

        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequestDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void authenticate() throws Exception {
        given(service.authenticate(any(AuthenticationRequestDto.class)))
                .willReturn(new AuthenticationResponseDto());

        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AuthenticationRequestDto())))
                .andExpect(status().isOk());
    }

    @Test
    void authenticateByGoogle() throws Exception {
        given(service.authenticateByGoogle(any(GoogleAuthenticationRequestDto.class)))
                .willReturn(new AuthenticationResponseDto());

        mockMvc.perform(post("/api/v1/login/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new GoogleAuthenticationRequestDto())))
                .andExpect(status().isOk());
    }

    @Test
    void changePassword() throws Exception {
        mockMvc.perform(put("/api/v1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PasswordRequestDto())))
                .andExpect(status().isOk());
    }
}