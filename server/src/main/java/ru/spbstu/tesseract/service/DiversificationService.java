package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.repository.DiversificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiversificationService {

    private final DiversificationRepository diversificationRepository;

    public List<Diversification> getAllDiversifications() {
        return diversificationRepository.findAll();
    }
}
