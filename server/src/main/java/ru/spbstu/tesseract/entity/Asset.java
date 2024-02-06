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
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public long getAssetAge() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        Duration duration = Duration.between(releaseDatetime, currentDateTime);
        return duration.toDays();
    }

    public int getScoreCompanyFoundation() {
        double yearsUntilFoundation = getCompany().getCompanyAge();

        if (yearsUntilFoundation < 1) {
            return 1;
        } else if (yearsUntilFoundation < 3) {
            return 2;
        } else if (yearsUntilFoundation < 6) {
            return 3;
        } else if (yearsUntilFoundation < 10) {
            return 4;
        }

        return 5;
    }

    public int getScoreCompanyRevenue() {
        long companyRevenue = getCompany().getRevenue();

        if (companyRevenue < 10_000_000_000L) {
            return 1;
        } else if (companyRevenue < 50_000_000_000L) {
            return 2;
        } else if (companyRevenue < 100_000_000_000L) {
            return 3;
        } else if (companyRevenue < 500_000_000_000L) {
            return 4;
        }

        return 5;
    }

    public int getScoreCompanyProfit() {
        long companyProfit = getCompany().getProfit();

        if (companyProfit < -1_000_000_000L) {
            return 1;
        } else if (companyProfit < 0L) {
            return 2;
        } else if (companyProfit < 1_000_000_000L) {
            return 3;
        } else if (companyProfit < 10_000_000_000L) {
            return 4;
        }

        return 5;
    }

    public int getScoreCompanyStaffCount() {
        int companyStaffCount = getCompany().getStaff();

        if (companyStaffCount < 10) {
            return 1;
        } else if (companyStaffCount < 50) {
            return 2;
        } else if (companyStaffCount < 100) {
            return 3;
        } else if (companyStaffCount < 500) {
            return 4;
        }

        return 5;
    }

    public int getScoreAssetRelease() {
        long daysUntilRelease = getAssetAge();

        if (daysUntilRelease < 10) {
            return 1;
        } else if (daysUntilRelease < 30) {
            return 2;
        } else if (daysUntilRelease < 90) {
            return 3;
        } else if (daysUntilRelease < 180) {
            return 4;
        }

        return 5;
    }

    public int getScoreAssetInterest() {
        if (interest > 17) {
            return 1;
        } else if (interest > 15) {
            return 2;
        } else if (interest > 13) {
            return 3;
        } else if (interest > 10) {
            return 4;
        }

        return 5;
    }

    public int getResultScore() {

        return getScoreCompanyFoundation() +
                getScoreCompanyRevenue() +
                getScoreCompanyProfit() +
                getScoreCompanyStaffCount() +
                getScoreAssetRelease() +
                getScoreAssetInterest();
    }

    public RiskType getRiskType() {
        int resultScore = getResultScore();

        if (resultScore < 18) {
            return RiskType.HIGH;
        } else if (resultScore < 21) {
            return RiskType.MIDDLE;
        }

        return RiskType.LOW;
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
