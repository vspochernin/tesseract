package ru.spbstu.tesseract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "assets_id_seq")
    private int id;

    private String title;
    private String description;
    private ZonedDateTime releaseDatetime;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Double interest;

    @OneToMany
    @JoinColumn(name = "asset_id")
    private List<Price> prices;

    @ManyToMany(mappedBy = "favourites")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    Set<User> users;

    public int getAssetPrice() {
        return prices.stream()
                .max(Comparator.comparing(Price::getSetDatetime))
                .map(Price::getPrice)
                .orElseThrow();
    }

    public int getAssetMonthPriceDiff(int currentPrice) {
        return currentPrice - getOldPrice(ZonedDateTime.now().minusMonths(1)).orElse(currentPrice);
    }

    public boolean isAssetFavourite() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return users.stream()
                .anyMatch(user -> user.getLogin().equals(userLogin));
    }

    public RiskType getRiskType() {
        // TODO: mock, will be implemented in 48.

        return RiskType.getById(id % RiskType.values().length);
    }

    public Optional<Integer> getOldPrice(ZonedDateTime atTheMoment) {
        return prices.stream()
                .filter(price -> price.getSetDatetime()
                        .toInstant()
                        .isBefore(atTheMoment.toInstant()))
                .max(Comparator.comparing(Price::getSetDatetime))
                .map(Price::getPrice);
    }
}
