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
    void givenRegisterRequest_whenRegister_thenStatusIsCreated() throws Exception {
        given(service.register(any(RegisterRequestDto.class)))
                .willReturn(new AuthenticationResponseDto());

        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .login("testUsername")
                .password("testPassword")
                .email("testEmail@example.com")
                .build();

        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenAuthenticationRequest_whenAuthenticate_thenStatusIsOk() throws Exception {
        given(service.authenticate(any(AuthenticationRequestDto.class)))
                .willReturn(new AuthenticationResponseDto());

        AuthenticationRequestDto requestDto = AuthenticationRequestDto.builder()
                .login("testUsername")
                .password("testPassword")
                .build();

        mockMvc.perform(post("/api/v1/login/tesseract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void givenGoogleAuthenticateRequest_whenAuthenticateByGoogle_thenStatusIsOk() throws Exception {
        given(service.authenticateByGoogle(any(GoogleAuthenticationRequestDto.class)))
                .willReturn(new AuthenticationResponseDto());

        GoogleAuthenticationRequestDto requestDto = GoogleAuthenticationRequestDto.builder()
                .token("testToken")
                .build();

        mockMvc.perform(post("/api/v1/login/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void givenPasswordRequest_whenChangePassword_thenStatusIsOk() throws Exception {
        PasswordRequestDto requestDto = PasswordRequestDto.builder()
                .oldPassword("oldPassword")
                .newPassword("newPassword")
                .build();

        mockMvc.perform(put("/api/v1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

}