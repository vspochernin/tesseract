package ru.spbstu.tesseract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.entity.SecretEntity;
import ru.spbstu.tesseract.service.SecretService;

import java.util.List;

@RestController
public class SecretController {

    @Autowired
    private SecretService secretService;

    @GetMapping("/secrets")
    public List<SecretEntity> getAllSecrets() {
        return secretService.getAllSecrets();
    }
}
