package ru.spbstu.tesseract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.service.FavouritesService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FavouritesController {

    private final FavouritesService favouritesService;

    @GetMapping("/favourites")
    public ResponseEntity<List<AssetShortDto>> favourites(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        List<AssetShortDto> assetShortDtos = favouritesService.getFavouriteAssets(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assetShortDtos);
    }

    @PostMapping("/favourites/{id}")
    public ResponseEntity addFavourite(@PathVariable String id) {
        try {
            int assetId = Integer.parseInt(id);
            favouritesService.addFavourite(assetId);
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/favourites/{id}")
    public ResponseEntity removeFavourite(@PathVariable String id) {
        try {
            int assetId = Integer.parseInt(id);
            favouritesService.removeFavourite(assetId);
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
