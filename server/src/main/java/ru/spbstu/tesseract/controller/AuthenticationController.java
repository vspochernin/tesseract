package ru.spbstu.tesseract.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.dto.AuthenticationRequestDto;
import ru.spbstu.tesseract.dto.AuthenticationResponseDto;
import ru.spbstu.tesseract.dto.GoogleAuthenticationRequestDto;
import ru.spbstu.tesseract.dto.PasswordRequestDto;
import ru.spbstu.tesseract.dto.RegisterRequestDto;
import ru.spbstu.tesseract.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(request));
    }

    @PostMapping("/login/tesseract")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @Valid @RequestBody AuthenticationRequestDto request)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.authenticate(request));
    }

    @PostMapping("/login/google")
    public ResponseEntity<AuthenticationResponseDto> authenticateByGoogle(
            @Valid @RequestBody GoogleAuthenticationRequestDto request)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.authenticateByGoogle(request));
    }

    @PutMapping("/password")
    public ResponseEntity changePassword(@Valid @RequestBody PasswordRequestDto request) {
        service.changePassword(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
