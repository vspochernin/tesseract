package ru.spbstu.tesseract.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.auth.config.JwtService;
import ru.spbstu.tesseract.entity.User;
import ru.spbstu.tesseract.repository.UserRepository;
import ru.spbstu.tesseract.exceptions.IncorrectLoginException;
import ru.spbstu.tesseract.exceptions.IncorrectPasswordException;
import ru.spbstu.tesseract.exceptions.LoginAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
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
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        User user = repository.findByLogin(request.getLogin())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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

