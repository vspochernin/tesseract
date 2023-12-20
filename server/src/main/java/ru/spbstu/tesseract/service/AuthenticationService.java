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
import ru.spbstu.tesseract.utils.FieldValidator;
import ru.spbstu.tesseract.dto.GoogleAuthenticationRequestDto;
import ru.spbstu.tesseract.dto.PasswordRequestDto;
import ru.spbstu.tesseract.dto.RegisterRequestDto;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.exception.GoogleTokenCannotBeVerified;
import ru.spbstu.tesseract.exception.PasswordDoesNotMatchException;
import ru.spbstu.tesseract.repository.UserRepository;
import ru.spbstu.tesseract.exception.IncorrectLoginException;
import ru.spbstu.tesseract.exception.IncorrectPasswordException;
import ru.spbstu.tesseract.exception.LoginAlreadyExistsException;

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
                        request.getPassword()
                )
        );

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
            throw new GoogleTokenCannotBeVerified(e.toString());
        }

        if (idToken == null) {
            throw new GoogleTokenCannotBeVerified();
        }

        String subject = idToken.getPayload().getSubject();
        String login = GOOGLE_USER_LOGIN_PREFIX + subject;

        User user = userRepository.findByLogin(login)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .login(login)
                            .password("")
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
            throw new IncorrectPasswordException();
        }

        User currentUser = User.getCurrentUser();
        String currentUserEncodedPassword = currentUser.getPassword();

        if (!passwordEncoder.matches(oldPassword, currentUserEncodedPassword)) {
            throw new PasswordDoesNotMatchException();
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(currentUser);
    }

    private void validateFields(String login, String email, String password) {
        if (!FieldValidator.isValidLogin(login)) {
            throw new IncorrectLoginException();
        }

        if (!FieldValidator.isValidPassword(password)) {
            throw new IncorrectPasswordException();
        }

        if (userRepository.existsByLogin(login)) {
            throw new LoginAlreadyExistsException();
        }

        if (userRepository.existsByEmail(email)) {
            // TODO: enable in prod.
            // throw new EmailAlreadyExistsException();
        }
    }
}

