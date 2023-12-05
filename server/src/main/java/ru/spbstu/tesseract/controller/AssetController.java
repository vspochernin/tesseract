package ru.spbstu.tesseract.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.dto.AssetLongDto;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.service.AssetService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/assets")
    public ResponseEntity<List<AssetShortDto>> assets(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        List<AssetShortDto> assetShortDtos = assetService.getAssets(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assetShortDtos);
    }

    @GetMapping("/assets/{id}")
    public ResponseEntity<AssetLongDto> asset(@PathVariable String id) {
        int assetId = Integer.parseInt(id);

        AssetLongDto assetLongDto = assetService.getAsset(assetId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assetLongDto);
    }
}
