package ru.spbstu.tesseract.entity;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assets")
public class Asset {

    private static final int MAX_ASSET_SCORE = 35;
    private static final int MIN_ASSET_SCORE = 0;
    private static final int LOW_RISK_THRESHOLD = 80;
    private static final int MIDDLE_RISK_THRESHOLD = 65;

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

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @OneToMany
    @JoinColumn(name = "asset_id")
    private List<Price> prices;

    @ManyToMany(mappedBy = "favourites")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    Set<User> users;

    public long getCurrentAssetPrice() {
        return prices.stream()
                .max(Comparator.comparing(Price::getSetDatetime))
                .map(Price::getPrice)
                .orElseThrow();
    }

    public long getAssetPriceDiff(ZonedDateTime since) {
        long currentPrice = getCurrentAssetPrice();
        return currentPrice - getOldPrice(since).orElse(currentPrice);
    }

    public long getAssetDayPriceDiff() {
        ZonedDateTime dayAgo = ZonedDateTime.now().minusDays(1);
        return getAssetPriceDiff(dayAgo);
    }

    public boolean isAssetFavourite() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return users.stream()
                .anyMatch(user -> user.getLogin().equals(userLogin));
    }

    public long getDaysSinceRelease() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        return Duration.between(releaseDatetime, currentDateTime).toDays();
    }

    public int getAssetScore() {
        int initialScore = getScoreOperatorInsertion() +
                getScoreCompanyFoundation() +
                getScoreCompanyRevenue() +
                getScoreCompanyProfit() +
                getScoreCompanyStaffCount() +
                getScoreAssetRelease() +
                getScoreAssetInterest();

        double scoreCoefficient = (double) initialScore / (MAX_ASSET_SCORE - MIN_ASSET_SCORE);
        return (int) (scoreCoefficient * 100L);
    }

    public RiskType getRiskType() {
        int resultScore = getAssetScore();

        if (resultScore >= LOW_RISK_THRESHOLD) {
            return RiskType.LOW;
        } else if (resultScore >= MIDDLE_RISK_THRESHOLD) {
            return RiskType.MIDDLE;
        }

        return RiskType.HIGH;
    }

    public Optional<Long> getOldPrice(ZonedDateTime atTheMoment) {
        return prices.stream()
                .filter(price -> price.getSetDatetime()
                        .toInstant()
                        .isBefore(atTheMoment.toInstant()))
                .max(Comparator.comparing(Price::getSetDatetime))
                .map(Price::getPrice);
    }

    private int getScoreOperatorInsertion() {
        long daysSinceInsertion = getOperator().getDaysSinceInsertion();

        if (daysSinceInsertion >= 700) {
            return 5;
        } else if (daysSinceInsertion >= 500) {
            return 4;
        } else if (daysSinceInsertion >= 300) {
            return 3;
        } else if (daysSinceInsertion >= 100) {
            return 2;
        } else if (daysSinceInsertion >= 50) {
            return 1;
        }

        return 0;
    }

    private int getScoreCompanyFoundation() {
        long yearsSinceFoundation = getCompany().getYearsSinceFoundation();

        if (yearsSinceFoundation >= 10) {
            return 5;
        } else if (yearsSinceFoundation >= 7) {
            return 4;
        } else if (yearsSinceFoundation >= 4) {
            return 3;
        } else if (yearsSinceFoundation >= 2) {
            return 2;
        } else if (yearsSinceFoundation >= 1) {
            return 1;
        }

        return 0;
    }

    private int getScoreCompanyRevenue() {
        long companyRevenue = getCompany().getRevenue();

        if (companyRevenue >= 500_000_000_000L) {
            return 5;
        } else if (companyRevenue >= 100_000_000_000L) {
            return 4;
        } else if (companyRevenue >= 50_000_000_000L) {
            return 3;
        } else if (companyRevenue >= 10_000_000_000L) {
            return 2;
        } else if (companyRevenue >= 5_000_000_000L) {
            return 1;
        }

        return 0;
    }

    private int getScoreCompanyProfit() {
        long companyProfit = getCompany().getProfit();

        if (companyProfit >= 10_000_000_000L) {
            return 5;
        } else if (companyProfit >= 1_000_000_000L) {
            return 4;
        } else if (companyProfit >= 0L) {
            return 3;
        } else if (companyProfit >= -1_000_000_000L) {
            return 2;
        } else if (companyProfit >= -3_000_000_000L) {
            return 1;
        }

        return 0;
    }

    private int getScoreCompanyStaffCount() {
        int companyStaffCount = getCompany().getStaff();

        if (companyStaffCount >= 500) {
            return 5;
        } else if (companyStaffCount >= 100) {
            return 4;
        } else if (companyStaffCount >= 50) {
            return 3;
        } else if (companyStaffCount >= 10) {
            return 2;
        } else if (companyStaffCount >= 5) {
            return 1;
        }

        return 0;
    }

    private int getScoreAssetRelease() {
        long daysSinceRelease = getDaysSinceRelease();

        if (daysSinceRelease >= 180) {
            return 5;
        } else if (daysSinceRelease >= 90) {
            return 4;
        } else if (daysSinceRelease >= 30) {
            return 3;
        } else if (daysSinceRelease >= 10) {
            return 2;
        } else if (daysSinceRelease >= 5) {
            return 1;
        }

        return 0;
    }

    private int getScoreAssetInterest() {
        double assetInterest = interest;

        if (assetInterest <= 10) {
            return 5;
        } else if (assetInterest <= 13) {
            return 4;
        } else if (assetInterest <= 15) {
            return 3;
        } else if (assetInterest <= 17) {
            return 2;
        } else if (assetInterest <= 20) {
            return 1;
        }

        return 0;
    }
}
