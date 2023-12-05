package ru.spbstu.tesseract.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.tesseract.dto.AssetShortDto;
import ru.spbstu.tesseract.service.FavouritesService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FavouritesController {

    private final FavouritesService favouritesService;

    @GetMapping("/favourites")
    public ResponseEntity<List<AssetShortDto>> favourites(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    )
    {
        List<AssetShortDto> assetShortDtos = favouritesService.getFavouriteAssets(pageNumber, pageSize);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assetShortDtos);
    }

    @PostMapping("/favourites/{id}")
    public ResponseEntity addFavourite(@PathVariable String id) {
        int assetId = Integer.parseInt(id);

        boolean wasAdded = favouritesService.addFavourite(assetId);

        HttpStatus httpStatus = wasAdded ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity
                .status(httpStatus)
                .build();
    }

    @DeleteMapping("/favourites/{id}")
    public ResponseEntity removeFavourite(@PathVariable String id) {
        int assetId = Integer.parseInt(id);
        favouritesService.removeFavourite(assetId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
