package ru.spbstu.tesseract.service;

import java.util.Optional;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spbstu.tesseract.dto.AuthenticationRequestDto;
import ru.spbstu.tesseract.dto.AuthenticationResponseDto;
import ru.spbstu.tesseract.dto.GoogleAuthenticationRequestDto;
import ru.spbstu.tesseract.dto.PasswordRequestDto;
import ru.spbstu.tesseract.dto.RegisterRequestDto;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

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
    private AuthenticationService authenticationService;

    @Test
    void givenValidRegisterRequest_whenRegister_thenSuccess() {
        RegisterRequestDto request = new RegisterRequestDto("vspochernin", "vspochernin@gmail.com", "qwe123");
        String encodedPassword = "encodedPassword";
        String jwtToken = "jwtToken";
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);
        when(userRepository.existsByLogin(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        AuthenticationResponseDto response = authenticationService.register(request);

        assertNotNull(response.getToken());
        assertEquals(jwtToken, response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void givenValidAuthRequest_whenAuthenticate_thenSuccess() {
        AuthenticationRequestDto request = new AuthenticationRequestDto("login", "password");
        User user = new User();
        String jwtToken = "jwtToken";
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        AuthenticationResponseDto response = authenticationService.authenticate(request);

        assertNotNull(response.getToken());
        assertEquals(jwtToken, response.getToken());
    }

    @Test
    void givenValidGoogleToken_whenAuthenticateByGoogle_thenSuccess() throws Exception {
        GoogleAuthenticationRequestDto request = new GoogleAuthenticationRequestDto("token");
        GoogleIdToken googleIdToken = mock(GoogleIdToken.class);
        GoogleIdToken.Payload payload = mock(GoogleIdToken.Payload.class);
        String subject = "subject";
        String jwtToken = "jwtToken";
        when(googleIdTokenVerifier.verify(request.getToken())).thenReturn(googleIdToken);
        when(googleIdToken.getPayload()).thenReturn(payload);
        when(payload.getSubject()).thenReturn(subject);
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);

        AuthenticationResponseDto response = authenticationService.authenticateByGoogle(request);

        assertNotNull(response.getToken());
        assertEquals(jwtToken, response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void givenValidPasswordChangeRequest_whenChangePassword_thenSuccess() {
        try (MockedStatic<User> mockedUser = mockStatic(User.class)) {
            User mockedCurrentUser = mock(User.class);
            when(mockedCurrentUser.getPassword()).thenReturn("oldPassword");
            mockedUser.when(User::getCurrentUser).thenReturn(mockedCurrentUser);
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");

            authenticationService.changePassword(new PasswordRequestDto("oldPassword", "newPassword123"));

            verify(userRepository).save(any(User.class));
        }
    }


}

