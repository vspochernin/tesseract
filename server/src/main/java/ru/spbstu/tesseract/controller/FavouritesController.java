package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.service.AssetService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FavouritesController {

    private final AssetService assetService;

    @GetMapping("/favourites")
    public ResponseEntity<List<AssetShortDto>> favourites(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        List<AssetShortDto> assetShortDtos = assetService.getFavouriteAssets(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assetShortDtos);
    }
}