package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.entity.Company;
import ru.spbstu.tesseract.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/testCompanies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companies);
    }
}
