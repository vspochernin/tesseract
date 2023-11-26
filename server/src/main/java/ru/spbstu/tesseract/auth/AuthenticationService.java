package ru.spbstu.tesseract.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.auth.config.JwtService;
import ru.spbstu.tesseract.auth.user.User;
import ru.spbstu.tesseract.auth.user.UserRepository;
import ru.spbstu.tesseract.exceptions.EmailAlreadyExistsException;
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
        User user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        if (!FieldValidator.isValidLogin(user.getLogin())) {
            throw new IncorrectLoginException();
        }

        if (!FieldValidator.isValidPassword(user.getLogin())) {
            throw new IncorrectPasswordException();
        }

        if (repository.existsByLogin(user.getLogin())) {
            throw new LoginAlreadyExistsException();
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

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
}

