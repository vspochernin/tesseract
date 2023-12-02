package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.service.DiversificationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DiversificationController {

    private final DiversificationService diversificationService;

    @GetMapping("/testDiversifications")
    public ResponseEntity<List<Diversification>> getAllDiversifications() {
        List<Diversification> allDiversifications = diversificationService.getAllDiversifications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allDiversifications);
    }
}
