package ru.spbstu.tesseract.auth.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.spbstu.tesseract.entity.Asset;
import ru.spbstu.tesseract.entity.Price;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Optional;

public class AssetUtils {

    public static Integer getAssetPrice(Asset asset) {
        return asset.getPrices().stream()
                .max(Comparator.comparing(Price::getSetDatetime))
                .map(Price::getPrice)
                .orElseThrow();
    }

    public static Integer getAssetMonthPriceDiff(Asset asset, Integer currentPrice) {
        return currentPrice - getAssetPriceMonthAgo(asset).orElse(currentPrice);
    }

    public static boolean isAssetFavourite(Asset asset) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return asset.getUsers().stream()
                .anyMatch(user -> user.getLogin().equals(userLogin));
    }

    private static Optional<Integer> getAssetPriceMonthAgo(Asset asset) {
        return asset.getPrices().stream()
                .filter(price -> price.getSetDatetime()
                        .toInstant()
                        .isBefore(ZonedDateTime.now().minusMonths(1).toInstant()))
                .max(Comparator.comparing(Price::getSetDatetime))
                .map(Price::getPrice);
    }
}
