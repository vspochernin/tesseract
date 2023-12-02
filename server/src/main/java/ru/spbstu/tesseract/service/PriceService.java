package ru.spbstu.tesseract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.repository.PriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }
}
