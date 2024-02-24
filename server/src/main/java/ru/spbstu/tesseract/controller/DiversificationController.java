package ru.spbstu.tesseract.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.dto.CreateDiversificationRequestDto;
import ru.spbstu.tesseract.dto.DiversificationLongDto;
import ru.spbstu.tesseract.dto.DiversificationShortDto;
import ru.spbstu.tesseract.service.DiversificationService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiversificationController {

    private final DiversificationService diversificationService;

    @GetMapping("/diversifications")
    public ResponseEntity<List<DiversificationShortDto>> diversifications(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize)
    {
        List<DiversificationShortDto> diversifications =
                diversificationService.getDiversifications(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diversifications);
    }

    @GetMapping("/diversifications/{id}")
    public ResponseEntity<DiversificationLongDto> diversification(@PathVariable String id) {
        int diversificationId = Integer.parseInt(id);

        DiversificationLongDto diversificationLongDto = diversificationService.getDiversification(diversificationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diversificationLongDto);
    }

    @PostMapping("/diversifications")
    public ResponseEntity createDiversification(@Valid @RequestBody CreateDiversificationRequestDto request) {
        diversificationService.createDiversification(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
