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
import ru.spbstu.tesseract.dto.CreatePortfolioRequestDto;
import ru.spbstu.tesseract.dto.PortfolioLongDto;
import ru.spbstu.tesseract.dto.PortfolioShortDto;
import ru.spbstu.tesseract.service.PortfolioService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/portfolios")
    public ResponseEntity<List<PortfolioShortDto>> portfolios(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize)
    {
        List<PortfolioShortDto> portfolios =
                portfolioService.getPortfolios(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(portfolios);
    }

    @GetMapping("/portfolios/{id}")
    public ResponseEntity<PortfolioLongDto> portfolio(@PathVariable String id) {
        int portfolioId = Integer.parseInt(id);

        PortfolioLongDto portfolioLongDto = portfolioService.getPortfolio(portfolioId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(portfolioLongDto);
    }

    @PostMapping("/portfolios")
    public ResponseEntity createPortfolio(@Valid @RequestBody CreatePortfolioRequestDto request) {
        portfolioService.createPortfolio(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
