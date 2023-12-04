package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.service.AssetService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/testAssets")
    public ResponseEntity<List<Asset>> getAllCompanies() {
        List<Asset> assets = assetService.getAllAssets();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assets);
    }
}
