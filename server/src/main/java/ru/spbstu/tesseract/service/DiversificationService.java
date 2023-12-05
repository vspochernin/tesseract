package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.entity.*;
import ru.spbstu.tesseract.repository.AssetRepository;
import ru.spbstu.tesseract.repository.DiversificationRepository;
import ru.spbstu.tesseract.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiversificationService {

    private final DiversificationRepository diversificationRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    public List<Diversification> getAllDiversifications() {
        return diversificationRepository.findAll();
    }

    public void addDiversification() {
        User user = userRepository.getReferenceById(1);

        Asset asset1 = assetRepository.getReferenceById(1);
        Asset asset2 = assetRepository.getReferenceById(2);
        Asset asset3 = assetRepository.getReferenceById(3);

        Diversification diversification = new Diversification(
                user,
                new Date(),
                RiskType.HIGH,
                123456,
                new DiversificationAsset(asset1, 1123),
                new DiversificationAsset(asset2, 2123),
                new DiversificationAsset(asset3, 3123)
        );

        diversificationRepository.save(diversification);
    }
}
