package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.dto.AuthenticationRequestDto;
import ru.spbstu.tesseract.dto.AuthenticationResponseDto;
import ru.spbstu.tesseract.auth.FieldValidator;
import ru.spbstu.tesseract.dto.PasswordRequestDto;
import ru.spbstu.tesseract.dto.RegisterRequestDto;
import ru.spbstu.tesseract.auth.config.JwtService;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.exception.PasswordDoesNotMatchException;
import ru.spbstu.tesseract.repository.UserRepository;
import ru.spbstu.tesseract.exception.IncorrectLoginException;
import ru.spbstu.tesseract.exception.IncorrectPasswordException;
import ru.spbstu.tesseract.exception.LoginAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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

        repository.save(user);
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

        User user = repository.findByLogin(request.getLogin())
                .orElseThrow();

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

        repository.save(currentUser);
    }

    private void validateFields(String login, String email, String password) {
        if (!FieldValidator.isValidLogin(login)) {
            throw new IncorrectLoginException();
        }

        if (!FieldValidator.isValidPassword(password)) {
            throw new IncorrectPasswordException();
        }

        if (repository.existsByLogin(login)) {
            throw new LoginAlreadyExistsException();
        }

        if (repository.existsByEmail(email)) {
            // TODO: enable in prod.
            // throw new EmailAlreadyExistsException();
        }
    }
}

