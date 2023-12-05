package ru.spbstu.tesseract.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.dto.DiversificationShortDto;
import ru.spbstu.tesseract.entity.Diversification;
import ru.spbstu.tesseract.service.DiversificationService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiversificationController {

    private final DiversificationService diversificationService;

    @GetMapping("/diversifications")
    public ResponseEntity<List<DiversificationShortDto>> diversifications(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    )
    {
        List<DiversificationShortDto> diversifications =
                diversificationService.getDiversifications(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diversifications);
    }

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
