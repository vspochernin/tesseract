package ru.spbstu.tesseract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.entity.SecretEntity;
import ru.spbstu.tesseract.repository.SecretRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecretService {

    @Autowired
    private SecretRepository secretRepository;

    public List<SecretEntity> getAllSecrets() {
        List<SecretEntity> secretEntities = new ArrayList<>();
        secretRepository.findAll().forEach(secretEntities::add);
        return secretEntities;
    }
}
