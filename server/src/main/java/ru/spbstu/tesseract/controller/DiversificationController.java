package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.service.DiversificationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
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

    @PostMapping("/testDiversifications")
    public ResponseEntity addDiversification() {
        diversificationService.addDiversification();

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
