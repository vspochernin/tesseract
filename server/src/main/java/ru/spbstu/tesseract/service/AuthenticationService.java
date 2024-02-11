package ru.spbstu.tesseract.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.dto.AuthenticationRequestDto;
import ru.spbstu.tesseract.dto.AuthenticationResponseDto;
import ru.spbstu.tesseract.dto.GoogleAuthenticationRequestDto;
import ru.spbstu.tesseract.dto.PasswordRequestDto;
import ru.spbstu.tesseract.dto.RegisterRequestDto;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.exception.TesseractErrorType;
import ru.spbstu.tesseract.exception.TesseractException;
import ru.spbstu.tesseract.repository.UserRepository;
import ru.spbstu.tesseract.utils.FieldValidator;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    private static final String GOOGLE_USER_LOGIN_PREFIX = "@google.";

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        String login = request.getLogin();
        String email = request.getEmail();
        String password = request.getPassword();

        validateFields(login, email, password);

        User user = User.builder()
                .login(login)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()));

        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto authenticateByGoogle(GoogleAuthenticationRequestDto request) {
        String token = request.getToken();
        GoogleIdToken idToken;
        try {
            idToken = googleIdTokenVerifier.verify(token);
        } catch (GeneralSecurityException | IOException e) {
            throw new TesseractException(TesseractErrorType.GOOGLE_TOKEN_CANNOT_BE_VERIFIED, e.toString());
        }

        if (idToken == null) {
            throw new TesseractException(TesseractErrorType.GOOGLE_TOKEN_CANNOT_BE_VERIFIED);
        }

        String subject = idToken.getPayload().getSubject();
        String login = GOOGLE_USER_LOGIN_PREFIX + subject;

        User user = userRepository.findByLogin(login)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .login(login)
                            .password("")
                            .email("")
                            .build();

                    userRepository.save(newUser);

                    return newUser;
                });

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public void changePassword(PasswordRequestDto request) {
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();

        if (!FieldValidator.isValidPassword(newPassword)) {
            throw new TesseractException(TesseractErrorType.INVALID_PASSWORD);
        }

        User currentUser = User.getCurrentUser();
        String currentUserEncodedPassword = currentUser.getPassword();

        if (!passwordEncoder.matches(oldPassword, currentUserEncodedPassword)) {
            throw new TesseractException(TesseractErrorType.PASSWORD_DOES_NOT_MATCH);
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(currentUser);
    }

    private void validateFields(String login, String email, String password) {
        if (!FieldValidator.isValidLogin(login)) {
            throw new TesseractException(TesseractErrorType.INVALID_LOGIN);
        }

        if (!FieldValidator.isValidPassword(password)) {
            throw new TesseractException(TesseractErrorType.INVALID_PASSWORD);
        }

        if (!FieldValidator.isValidEmail(email)) {
            throw new TesseractException(TesseractErrorType.INVALID_EMAIL);
        }

        if (userRepository.existsByLogin(login)) {
            throw new TesseractException(TesseractErrorType.LOGIN_EXISTS);
        }

        if (userRepository.existsByEmail(email)) {
            throw new TesseractException(TesseractErrorType.EMAIL_EXISTS);
        }
    }
}

