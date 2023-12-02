package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.spbstu.tesseract.entity.Price;
import ru.spbstu.tesseract.service.PriceService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/testPrices")
    public ResponseEntity<List<Price>> getAllPrices() {
        List<Price> allPrices = priceService.getAllPrices();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPrices);
    }
}
