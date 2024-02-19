package ru.spbstu.tesseract.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spbstu.tesseract.dto.*;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @InjectMocks
    private AuthenticationService authService;

    @Test
    void testRegister() {
        // Arrange
        RegisterRequestDto request = new RegisterRequestDto();
        request.setLogin("login");
        request.setEmail("email@example.com");
        request.setPassword("password1");

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any())).thenReturn("token");

        // Act
        AuthenticationResponseDto result = authService.register(request);

        // Assert
        assertEquals("token", result.getToken());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testAuthenticate() {
        // Arrange
        AuthenticationRequestDto request = new AuthenticationRequestDto();
        request.setLogin("test");
        request.setPassword("password1");

        User user = new User();
        user.setLogin("test");
        user.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("token");

        // Act
        AuthenticationResponseDto result = authService.authenticate(request);

        // Assert
        assertEquals("token", result.getToken());
    }
}